package org.architecture.sport.domain.functionnal

import arrow.core.Either
import arrow.core.left
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Material
import org.architecture.sport.domain.ports.client.MaterialApi
import org.architecture.sport.domain.ports.server.CenterSportPersistenceSpi
import org.architecture.sport.domain.ports.server.MaterialPersistenceSpi
import org.architecture.sport.domain.utils.toList
import org.architecture.sport.domain.validation.MaterialValidation
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class MaterialService(
    private val materialValidation: MaterialValidation,
    private val materialPersistenceSpi: MaterialPersistenceSpi,
    private val centerSportPersistenceSpi: CenterSportPersistenceSpi,
) : MaterialApi {

    override fun createMaterial(material: Material): Either<ApplicationError, Material> {
        val validation = materialValidation.validateMaterial(material)
        if (validation.errors.isNotEmpty()) {
            return ApplicationError(
                context = "Material",
                message = "Material is not valid",
                value = material
            ).left()
        }
        return materialPersistenceSpi.save(material)
    }

    override fun getMaterial(id: UUID?): List<Material> {
        return if (id != null) {
            materialPersistenceSpi.findById(id).toList()
        } else {
            materialPersistenceSpi.findAll()
        }
    }

    override fun distributeMaterial(id: UUID, centerSportId: UUID): Either<ApplicationError, Material> {
        return applyPropertyToMaterial(id, centerSportId)
    }


    override fun maintenanceMaterial(id: UUID): Either<ApplicationError, Material> {
        val material = materialPersistenceSpi.findById(id) ?: return ApplicationError(
            context = "Material",
            message = "Material not found",
            value = id
        ).left()
        val newMaterial = material.listMaintenanceTime.plus(LocalDateTime.now())
        return try {
            materialPersistenceSpi.save(material.copy(listMaintenanceTime = newMaterial))
        } catch (e: Exception) {
            ApplicationError(
                context = "Material",
                message = "Material is not save after maintenance",
                value = material
            ).left()
        }
    }

    override fun changeProprietyCenterSport(id: UUID, centerSportId: UUID): Either<ApplicationError, Material> {
        return applyPropertyToMaterial(id, centerSportId)
    }


    private fun applyPropertyToMaterial(
        materialId: UUID,
        centerSportId: UUID
    ): Either<ApplicationError, Material> {
        val material = materialPersistenceSpi.findById(materialId) ?: return ApplicationError(
            context = "Material",
            message = "Material not found",
            value = materialId
        ).left()
        val centerSport = centerSportPersistenceSpi.findById(centerSportId)
            ?: return ApplicationError(
                context = "CenterSport",
                message = "CenterSport not found",
                value = centerSportId
            ).left()
        return materialPersistenceSpi.save(material.copy(centerSportId = centerSport.id))
    }

}