package org.architecture.sport.domain.functionnal

import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.generate.*
import org.architecture.sport.domain.model.TransactionStatus
import org.architecture.sport.domain.ports.server.*
import org.architecture.sport.domain.validation.SessionValidation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.*

class SessionServiceTest {

    private val sessionValidation = SessionValidation()
    private val materialPersistenceSpi = mock<MaterialPersistenceSpi> { }
    private val presentationPersistenceSpi = mock<PresentationPersistenceSpi> { }
    private val sessionPersistenceSpi = mock<SessionPersistenceSpi> { }
    private val userPersistenceSpi = mock<UserPersistenceSpi> {}
    private val enterprisePersistenceSpi = mock<EnterprisePersistenceSpi> { }
    private val sessionService = SessionService(
        sessionValidation,
        materialPersistenceSpi,
        presentationPersistenceSpi,
        sessionPersistenceSpi,
        userPersistenceSpi,
        enterprisePersistenceSpi,
    )

    @Test
    fun createSession_WithNotGoodOpenHour() {
        //Given
        val startDateNotGood = LocalDateTime.of(2030, 12, 1, 7, 1)
        val generateSession = generateSession().copy(startDate = startDateNotGood)
        val applicationError = generateApplicationError(
            context = "Session",
            message = "Session not valid",
            value = generateSession
        ).left()
        //Then
        val result = sessionService.createSession(generateSession)
        assertEquals(applicationError, result)

    }


    @Test
    fun createSession_WithNotMaterielIsAvailable() {
        //Given
        val idHistory = UUID.randomUUID()
        val localDateTime = LocalDateTime.now()
        val generateCenterSport = generateCenterSport()
        val generateSession = generateSession().copy(centerSportId = generateCenterSport.id)
        val saveGenerateSession = generateSession.copy(
            history = listOf(
                generateHistory(
                    date = localDateTime,
                    action = "Session ${generateSession.id} created"
                ).copy(id = idHistory)
            )
        )
        val expectedSession = generateSession.copy(
            history = listOf(
                generateHistory(
                    date = localDateTime,
                    action = "Session ${generateSession.id} created"
                ).copy(id = idHistory)
            )
        )
        //When
        whenever(
            sessionPersistenceSpi.isMaterielAvailableNotInOverSession(
                generateSession.startDate,
                generateSession.endDate,
                generateSession.material!!
            )
        ).thenReturn(true)
        whenever(
            materialPersistenceSpi.isGoodCenterSport(
                generateSession.centerSportId,
                generateSession.material!!
            )
        ).thenReturn(true)
        whenever(
            sessionPersistenceSpi.isPrestationAvailableNotInOverSession(
                generateSession.startDate,
                generateSession.endDate,
                generateSession.prestation!!
            )
        ).thenReturn(true)
        whenever(
            presentationPersistenceSpi.isGoodCenterSport(
                generateSession.centerSportId,
                generateSession.prestation!!,
            )
        ).thenReturn(true)
        whenever(sessionPersistenceSpi.save(any())).thenReturn(saveGenerateSession.right())
        //then
        val result = sessionService.createSession(generateSession)
        assertEquals(expectedSession.right(), result)
    }

    @Test
    fun joinSession() {
        // Given
        val idSession = UUID.randomUUID()
        val generateSession = generateSession().copy(id = idSession)
        val generatePrestation = generatePrestation().copy(id = generateSession.prestation!!)
        val generateMaterial = generateMaterial().copy(id = generateSession.material!!)
        val idUser = UUID.randomUUID()
        val generateUser = generateUser().copy(id = idUser, money = 1000.0)
        val price = sessionService.isHasMoneyEnterprise(
            generateSession,
            generateUser,
            generatePrestation.price,
            generateMaterial.caution,
        ).second
        val expectedUser = generateUser().copy(id = idUser, money = 1000.0 - price)
        val sessionWithUser =
            generateSession().copy(id = idSession, participants = listOf(idUser to TransactionStatus.PAYMENT))
        // When
        whenever(sessionPersistenceSpi.getSession(idSession)).thenReturn(generateSession)
        whenever(userPersistenceSpi.findById(idUser)).thenReturn(generateUser)
        whenever(materialPersistenceSpi.findById(generateSession.material!!)).thenReturn(generateMaterial)
        whenever(presentationPersistenceSpi.findById(generateSession.prestation!!)).thenReturn(generatePrestation)
        whenever(enterprisePersistenceSpi.isUserAlreadyInEnterprise(idUser)).thenReturn(true)
        whenever(userPersistenceSpi.save(expectedUser)).thenReturn(expectedUser.right())
        whenever(sessionPersistenceSpi.save(any())).thenReturn(sessionWithUser.right())
        // Then
        val result = sessionService.joinSession(idSession, idUser)
        assertEquals(sessionWithUser.right(), result)


    }

    @Test
    fun unJoinSession() {
        // Given
        val generateUser = generateUser()
        val generateSession =
            generateSession().copy(participants = listOf(generateUser.id to TransactionStatus.PAYMENT))
        val expectedSession = generateSession().copy(
            participants = emptyList(),
            history = listOf(generateHistory(action = "User ${generateUser.id} unjoin session ${generateSession.id}"))
        )
        // When
        whenever(sessionPersistenceSpi.getSession(generateSession.id)).thenReturn(generateSession)
        whenever(userPersistenceSpi.findById(generateUser.id)).thenReturn(generateUser)
        whenever(materialPersistenceSpi.findById(generateSession.material!!)).thenReturn(generateMaterial().copy(caution = 10.0))
        whenever(presentationPersistenceSpi.findById(generateSession.prestation!!)).thenReturn(
            generatePrestation().copy(
                price = 10.0
            )
        )
        whenever(sessionPersistenceSpi.save(any())).thenReturn(expectedSession.right())
        //then
        val result = sessionService.unJoinSession(generateSession.id, generateUser.id)
        assertEquals(expectedSession.right(), result)

    }

    @Test
    fun returnCaution() {
        //given
        val caution = 10.0
        val generatedUser = generateUser().copy(money = 1000.0)
        val generatedSession = generateSession().copy(participants = listOf(generatedUser.id to TransactionStatus.PAYMENT))
        //when
        whenever(sessionPersistenceSpi.getSession(generatedSession.id)).thenReturn(generatedSession)
        whenever(materialPersistenceSpi.findById(generatedSession.material!!)).thenReturn(generateMaterial().copy(caution = 10.0))
        whenever(userPersistenceSpi.findById(generatedSession.participants.first().first)).thenReturn(generatedUser)
        whenever(userPersistenceSpi.save(generatedUser.copy(money = generatedUser.money + caution))).thenReturn(generatedUser.copy(money = generatedUser.money + caution).right())
        whenever(sessionPersistenceSpi.save(any())).thenReturn(generatedSession.copy(participants = emptyList()).right())
        //then
        val result = sessionService.returnCaution(generatedSession.id, generatedSession.participants.first().first)
        assertEquals(emptyList<UUID>(), result.getOrNull()?.participants)

    }

}