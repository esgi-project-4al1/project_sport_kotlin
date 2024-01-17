package org.architecture.sport.server.adapter

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Session
import org.architecture.sport.domain.ports.server.SessionPersistenceSpi
import org.architecture.sport.server.mapper.SessionEntityMapper
import org.architecture.sport.server.repository.SessionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class SessionAdapter(
    private val sessionRepository: SessionRepository,
    private val sessionMapper: SessionEntityMapper,
) : SessionPersistenceSpi {


    override fun getSession(sessionId: UUID?): Session? {
        return if (sessionId == null) {
            sessionRepository.findAll().map { sessionMapper.toDomain(it) }.firstOrNull()
        } else {
            sessionRepository.findById(sessionId).map { sessionMapper.toDomain(it) }.orElse(null)
        }
    }

    override fun getSessions(): List<Session> {
        return sessionRepository.findAll().stream()
            .map { sessionMapper.toDomain(it) }
            .toList()
    }


    override fun getSessionsByCenterSport(centerSportId: UUID): List<Session> {
        return sessionRepository.findAllByCenterSportId(centerSportId).stream()
            .map { sessionMapper.toDomain(it) }
            .toList()
    }

    override fun isMaterielAvailableNotInOverSession(
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        materielId: UUID
    ): Boolean {
        return  sessionRepository.findOverlappingSessionsByMaterial(startDateTime, endDateTime)
            .none { it.material == materielId }
    }

    override fun isPrestationAvailableNotInOverSession(
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        prestationId: UUID
    ): Boolean {
        return sessionRepository.findOverlappingSessions(startDateTime, endDateTime)
        .none { it.prestation == prestationId }
    }

    override fun save(session: Session): Either<ApplicationError, Session> {
        try {
            val sessionEntity = sessionMapper.toEntity(session)
            val sessionEntitySaved = sessionRepository.save(sessionEntity)
            return sessionMapper.toDomain(sessionEntitySaved).right()
        } catch (e: Exception) {
            return ApplicationError(
                context = "SessionAdapter",
                message = "Error while saving session",
            ).left()
        }
    }

    override fun findById(id: UUID): Session? {
        return sessionRepository.findById(id).map { sessionMapper.toDomain(it) }.orElse(null)
    }
}