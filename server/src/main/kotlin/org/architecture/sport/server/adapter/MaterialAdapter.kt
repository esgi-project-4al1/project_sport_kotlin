package org.architecture.sport.server.adapter

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Material
import org.architecture.sport.domain.ports.server.MaterialPersistenceSpi
import org.architecture.sport.server.mapper.MaterialEntityMapper
import org.architecture.sport.server.repository.MaterialRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MaterialAdapter(
    private val materialRepository: MaterialRepository,
    private val materialMapper: MaterialEntityMapper,
) : MaterialPersistenceSpi {
    override fun findById(id: UUID): Material? {
        return materialRepository.findById(id).map { materialMapper.toDomain(it) }.orElse(null)
    }

    override fun findAll(): List<Material> {
        return materialRepository.findAll().stream().map { materialMapper.toDomain(it) }.toList()
    }

    override fun isGoodCenterSport(centerSportId: UUID, materielId: UUID): Boolean {
        val result = materialRepository.findById(materielId)
        return if (result.isPresent) {
            val material = result.get()
            material.centerSportId == centerSportId
        } else {
            false
        }
    }

    override fun save(o: Material): Either<ApplicationError, Material> {
        try {
            val materialEntity = materialMapper.toEntity(o)
            val saveMaterial = materialMapper.toDomain(materialRepository.save(materialEntity)).right()
            return saveMaterial
        } catch (e: Exception) {
            return ApplicationError(
                "Error when saving material",
                "Error when saving material"
            ).left()

        }
    }
}