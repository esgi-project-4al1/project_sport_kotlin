package org.architecture.sport.domain.ports.client

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Material
import java.util.UUID

interface MaterialApi {

    fun createMaterial(
        material: Material
    ): Either<ApplicationError, Material>

    fun getMaterial(
        id: UUID?
    ): List<Material>

    fun distributeMaterial(
        id: UUID,
        centerSportId: UUID
    ): Either<ApplicationError, Material>

    fun maintenanceMaterial(
        id: UUID
    ): Either<ApplicationError, Material>


    fun changeProprietyCenterSport(
        id: UUID,
        centerSportId: UUID
    ): Either<ApplicationError, Material>
}