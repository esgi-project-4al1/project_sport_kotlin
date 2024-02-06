package org.architecture.sport.domain.ports.server

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.CenterSport
import java.util.UUID

interface CenterSportPersistenceSpi : PersistenceSpi<CenterSport, UUID> {

    override fun save(centerSport: CenterSport): Either<ApplicationError, CenterSport>

    override fun findById(id: UUID): CenterSport?

    fun findAll(): List<CenterSport>
}