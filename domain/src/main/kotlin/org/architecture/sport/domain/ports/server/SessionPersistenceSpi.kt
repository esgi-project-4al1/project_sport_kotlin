package org.architecture.sport.domain.ports.server

import org.architecture.sport.domain.model.Session
import java.time.LocalDateTime
import java.util.*

interface SessionPersistenceSpi: PersistenceSpi<Session, UUID> {

    fun createSession(session: Session): Session

    fun getSession(sessionId: UUID?): Session?

    fun getSessions(): List<Session>

    fun joinSession(sessionId: UUID, userId: UUID): Session

    fun unJoinSession(sessionId: UUID, userId: UUID): Session


    fun getSessionsByCenterSport(centerSportId: UUID): List<Session>


    fun isMaterielAvailableNotInOverSession(startDateTime: LocalDateTime, endDateTime: LocalDateTime, materielId: UUID): Boolean

    fun isPrestationAvailableNotInOverSession(startDateTime: LocalDateTime, endDateTime: LocalDateTime, prestationId: UUID): Boolean
}