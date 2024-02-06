package org.architecture.sport.server.mapper

import org.architecture.sport.domain.model.Prestation
import org.architecture.sport.server.entity.PresentationEntity
import org.springframework.stereotype.Service

@Service
class PrestationEntityMapper {

    fun toDomain(
        presentationEntity: PresentationEntity
    ): Prestation {
        return Prestation(
            id = presentationEntity.id,
            name = presentationEntity.name,
            centerSportId = presentationEntity.centerSportId,
            price = presentationEntity.price,
            description = presentationEntity.description,
            history = presentationEntity.history
        )
    }


    fun toEntity(
        prestation: Prestation
    ): PresentationEntity {
        return PresentationEntity(
            id = prestation.id,
            name = prestation.name,
            centerSportId = prestation.centerSportId,
            price = prestation.price,
            description = prestation.description,
            history = prestation.history
        )
    }
}