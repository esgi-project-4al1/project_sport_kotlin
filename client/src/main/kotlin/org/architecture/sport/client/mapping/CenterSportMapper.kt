package org.architecture.sport.client.mapping

import org.architecture.sport.client.dto.CenterSportDto
import org.architecture.sport.domain.model.Address
import org.architecture.sport.domain.model.CenterSport
import org.springframework.stereotype.Service

@Service
class CenterSportMapper {

    fun toDomain(
        centerSportDto: CenterSportDto,
    ): CenterSport {
        return CenterSport(
            name = centerSportDto.name,
            address = Address(
                street = centerSportDto.street,
                city = centerSportDto.city,
                postalCode = centerSportDto.postalCode,
                country = centerSportDto.country
            )
        )
    }

}