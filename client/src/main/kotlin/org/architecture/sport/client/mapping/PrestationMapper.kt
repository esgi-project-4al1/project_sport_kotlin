package org.architecture.sport.client.mapping

import org.architecture.sport.client.dto.PrestationDto
import org.architecture.sport.domain.model.Prestation
import org.springframework.stereotype.Service
import java.util.*

@Service
class PrestationMapper {

    fun toDomain(
        prestationDto: PrestationDto,
    ): Prestation{
        return Prestation(
            name = prestationDto.name,
            centerSportId = UUID.fromString(prestationDto.centerSportId),
            price = prestationDto.price,
            description = prestationDto.description,
        )
    }
}