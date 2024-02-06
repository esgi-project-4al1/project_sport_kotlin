package org.architecture.sport.server.mapper

import org.architecture.sport.domain.model.CenterSport
import org.architecture.sport.server.entity.CenterSportEntity
import org.springframework.stereotype.Service

@Service
class CenterSportEntityMapper {

    fun toDomain(
        centerSportEntity: CenterSportEntity,
    ): CenterSport {
        return CenterSport(
            id = centerSportEntity.id,
            name = centerSportEntity.name,
            address = centerSportEntity.address,
        )
    }


    fun toServer(
        centerSport: CenterSport,
    ): CenterSportEntity {
        return CenterSportEntity(
            id = centerSport.id,
            name = centerSport.name,
            address = centerSport.address,
        )
    }
}