package org.architecture.sport.client.mapping

import org.architecture.sport.client.dto.RoomDto
import org.architecture.sport.domain.model.Room
import org.springframework.stereotype.Service

@Service
class RoomMapper {


    fun toDomain(roomDto: RoomDto): Room {
        return Room(
            name = roomDto.name,
            centerSportId = roomDto.centerSportId,
            price = roomDto.price,
        )
    }
}