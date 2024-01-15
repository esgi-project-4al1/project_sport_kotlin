package org.architecture.sport.domain.ports.server

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Material
import java.util.UUID

interface MaterialPersistenceSpi : PersistenceSpi<Material, UUID> {

    override fun findById(
        id: UUID
    ): Material?

    fun findAll(): List<Material>


    fun isGoodCenterSport(
        centerSportId: UUID,
        materielId: UUID
    ): Boolean
}