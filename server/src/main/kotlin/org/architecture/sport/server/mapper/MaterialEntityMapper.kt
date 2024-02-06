package org.architecture.sport.server.mapper

import org.architecture.sport.domain.model.Material
import org.architecture.sport.server.entity.MaterialEntity
import org.springframework.stereotype.Service

@Service
class MaterialEntityMapper {

    fun toDomain(materialEntity: MaterialEntity): Material {
        return Material(
            id = materialEntity.id,
            name = materialEntity.name,
            quantity = materialEntity.quantity,
            centerSportId = materialEntity.centerSportId,
            caution = materialEntity.caution,
            createdAt = materialEntity.createdAt,
            listMaintenanceTime = materialEntity.listMaintenanceTime,
        )
    }

    fun toEntity(material: Material): MaterialEntity {
        return MaterialEntity(
            id = material.id,
            name = material.name,
            quantity = material.quantity,
            centerSportId = material.centerSportId,
            caution = material.caution,
            createdAt = material.createdAt,
            listMaintenanceTime = material.listMaintenanceTime,
        )
    }
}