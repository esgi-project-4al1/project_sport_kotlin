package org.architecture.sport.client.mapping

import org.architecture.sport.client.dto.CreateMaterielDto
import org.architecture.sport.domain.model.Material
import org.springframework.stereotype.Service

@Service
class MaterialMapper {


    fun createMaterialToDomain(
        createMaterielDto: CreateMaterielDto
    ): Material {
        return Material(
            name = createMaterielDto.name,
            quantity = createMaterielDto.quantity,
            caution = createMaterielDto.caution
        )
    }
}