package org.architecture.sport.domain.functionnal


import arrow.core.Either
import arrow.core.left
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.History
import org.architecture.sport.domain.model.Session
import org.architecture.sport.domain.model.TransactionStatus
import org.architecture.sport.domain.model.User
import org.architecture.sport.domain.ports.client.SessionApi
import org.architecture.sport.domain.ports.server.*
import org.architecture.sport.domain.utils.toList
import org.architecture.sport.domain.validation.SessionValidation
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class SessionService(
    private val sessionValidation: SessionValidation,
    private val materialPersistenceSpi: MaterialPersistenceSpi,
    private val presentationPersistenceSpi: PresentationPersistenceSpi,
    private val sessionPersistenceSpi: SessionPersistenceSpi,
    private val userPersistenceSpi: UserPersistenceSpi,
    private val enterprisePersistenceSpi: EnterprisePersistenceSpi
) : SessionApi {
    override fun createSession(session: Session): Either<ApplicationError, Session> {
        val validation = sessionValidation.validateSession(session)
        return if (validation.errors.isEmpty() && !availableSession(session)) {
            sessionPersistenceSpi.save(
                session.createNewHistory(
                    comment = "Session ${session.id} created"
                )
            )
        } else {
            ApplicationError(
                context = "Session",
                message = "Session not valid",
                value = session
            ).left()
        }


    }

    override fun getSession(sessionId: UUID?): List<Session> {
        return if (sessionId == null) {
            sessionPersistenceSpi.getSessions()
        } else {
            sessionPersistenceSpi.getSession(sessionId).toList()
        }
    }

    override fun joinSession(sessionId: UUID, userId: UUID): Either<ApplicationError, Session> {
        val session = sessionPersistenceSpi.getSession(sessionId) ?: return ApplicationError(
            context = "Session",
            message = "Session not found",
            value = sessionId
        ).left()
        val user = userPersistenceSpi.findById(userId) ?: return ApplicationError(
            context = "User",
            message = "User not found",
            value = userId
        ).left()
        val materialCaution = session.material?.let { materialPersistenceSpi.findById(it)?.caution } ?: 0.0


        if (session.participants.any { it.first == user.id }) return ApplicationError(
            context = "Session",
            message = "User already in session",
            value = userId
        ).left()

        userPersistenceSpi.save(user.copy(money = user.money - (session.price + materialCaution))).fold(
            { return it.left() },
            { }
        )

        val isEnterprise = isUserInEnterprise(session, user)

        if (!isHasMoney(session, user, materialCaution, isEnterprise)) return ApplicationError(
            context = "Session",
            message = "User has not enough money",
            value = userId
        ).left()


        return payment(session, user, materialCaution, isEnterprise)
    }

    override fun unJoinSession(sessionId: UUID, userId: UUID): Either<ApplicationError, Session> {
        val session = sessionPersistenceSpi.getSession(sessionId) ?: return ApplicationError(
            context = "Session",
            message = "Session not found",
            value = sessionId
        ).left()
        val user = userPersistenceSpi.findById(userId) ?: return ApplicationError(
            context = "User",
            message = "User not found",
            value = userId
        ).left()

        return sessionValidation.validationUnJoinParticipant(session).fold(
            { it.left() },
            {
                val sessionUpdate = session.removeParticipant(user.id)
                sessionPersistenceSpi.save(
                    sessionUpdate.createNewHistory(
                        comment = "User ${user.id} unjoin session ${session.id}"
                    )
                )
            }
        )
    }

    override fun getSessionsByCenterSport(centerSportId: UUID): List<Session> {
        return sessionPersistenceSpi.getSessionsByCenterSport(centerSportId)
    }

    override fun returnCaution(sessionId: UUID, userId: UUID): Either<ApplicationError, Session> {
        val session = sessionPersistenceSpi.getSession(sessionId) ?: return ApplicationError(
            context = "Session",
            message = "Session not found",
            value = sessionId
        ).left()
        val user = userPersistenceSpi.findById(userId) ?: return ApplicationError(
            context = "Session",
            message = "User not found in session",
            value = userId
        ).left()
        val materialCaution = session.material?.let { materialPersistenceSpi.findById(it)?.caution } ?: 0.0
        return userPersistenceSpi.save(user.copy(money = user.money + materialCaution)).fold(
            { it.left() },
            {
                val sessionUpdate = session.rentMaterial(user.id)
                sessionPersistenceSpi.save(
                    sessionUpdate.createNewHistory(
                        comment = "User ${user.id} return caution session ${session.id}"
                    )
                )
            }
        )
    }

    private fun materielIsAvailable(session: Session): Boolean {
        if (session.material == null) return true
        sessionPersistenceSpi.isMaterielAvailableNotInOverSession(session.startDate, session.endDate, session.material)
            .run { if (!this) return false }
        return materialPersistenceSpi.isGoodCenterSport(session.centerSportId, session.material)
    }

    private fun prestationIsAvailable(session: Session): Boolean {
        if (session.prestation == null) return true
        sessionPersistenceSpi.isPrestationAvailableNotInOverSession(
            session.startDate,
            session.endDate,
            session.material!!
        ).run { if (!this) return false }
        return presentationPersistenceSpi.isGoodCenterSport(session.centerSportId, session.prestation)
    }


    private fun availableSession(session: Session): Boolean {
        return materielIsAvailable(session) && prestationIsAvailable(session)
    }

    private fun isHasMoney(session: Session, user: User, priceCaution: Double, isEnterprise: Boolean): Boolean {
        return if (isEnterprise) {
            isHasMoneyEnterprise(session, user, priceCaution)
        } else {
            isHasMoneyNotEnterprise(session, user, priceCaution)
        }
    }

    private fun isUserInEnterprise(session: Session, user: User): Boolean {
        return enterprisePersistenceSpi.isUserIsInEnterprise(session.centerSportId, user.id)
    }


    private fun payment(
        session: Session,
        user: User,
        priceCaution: Double,
        isEnterprise: Boolean
    ): Either<ApplicationError, Session> {
        return if (isEnterprise) {
            userPersistenceSpi.save(user.copy(money = user.money - (session.price * PERCENTAGE_FOR_ENTERPRISE + priceCaution)))
                .fold(
                    { it.left() },
                    {
                        val sessionUpdate = session.addParticipant(user.id)
                        sessionPersistenceSpi.save(
                            sessionUpdate.createNewHistory(
                                comment = "User ${user.id} join session ${session.id}"
                            )
                        )
                    }
                )
        } else {
            userPersistenceSpi.save(user.copy(money = user.money - (session.price + priceCaution))).fold(
                { it.left() },
                {
                    val sessionUpdate = session.addParticipant(user.id)
                    sessionPersistenceSpi.save(
                        sessionUpdate.createNewHistory(
                            comment = "User ${user.id} join session ${session.id}"
                        )
                    )
                }
            )
        }
    }


    private fun isHasMoneyNotEnterprise(session: Session, user: User, priceCaution: Double): Boolean {
        return user.money >= session.price + priceCaution
    }

    private fun isHasMoneyEnterprise(session: Session, user: User, priceCaution: Double): Boolean {
        return user.money >= session.price * PERCENTAGE_FOR_ENTERPRISE + priceCaution
    }


    private fun Session.removeParticipant(userId: UUID): Session {
        return this.copy(participants = this.participants.filter { it.first != userId })
    }

    private fun Session.addParticipant(userId: UUID): Session {
        return this.copy(participants = this.participants + (userId to TransactionStatus.PAYMENT))
    }

    private fun Session.createNewHistory(
        comment: String,
    ) = this.copy(
        history = this.history + History(
            date = LocalDateTime.now(),
            action = comment,
        )
    )


    private fun Session.rentMaterial(userId: UUID): Session {
        val removeParticipant = this.removeParticipant(userId)
        return this.copy(participants = removeParticipant.participants + (userId to TransactionStatus.CAUTION_RETURNED))
    }

    companion object {
        private const val PERCENTAGE_FOR_ENTERPRISE = 0.2
    }

}