package org.architecture.sport.domain.ports.client

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Session
import java.util.*

interface SessionApi {

    fun createSession(
        session: Session
    ): Either<ApplicationError, Session>


    fun getSession(sessionId: UUID?): List<Session>


    fun joinSession(sessionId: UUID, userId: UUID): Either<ApplicationError, Session>

    fun unJoinSession(sessionId: UUID, userId: UUID): Either<ApplicationError, Session>


    fun getSessionsByCenterSport(centerSportId: UUID): List<Session>
}