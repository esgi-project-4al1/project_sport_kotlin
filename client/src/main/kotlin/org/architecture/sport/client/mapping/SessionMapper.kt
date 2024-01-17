package org.architecture.sport.client.mapping

import org.architecture.sport.client.dto.SessionDto
import org.architecture.sport.domain.model.Session
import org.springframework.stereotype.Service
import java.util.*

@Service
class SessionMapper {

    fun toDomain(
        sessionDto: SessionDto
    ): Session {
        return Session(
            name = sessionDto.name,
            centerSportId = UUID.fromString(sessionDto.centerSportId),
            price = sessionDto.price,
            description = sessionDto.description,
            prestation = UUID.fromString(sessionDto.prestation),
            material = UUID.fromString(sessionDto.material),
            startDate = sessionDto.startDate,
            endDate = sessionDto.endDate,
            maxParticipants = sessionDto.maxParticipants,
            minParticipants = sessionDto.minParticipants,
        )
    }
}