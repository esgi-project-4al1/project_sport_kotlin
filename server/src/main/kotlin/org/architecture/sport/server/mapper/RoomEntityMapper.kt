package org.architecture.sport.server.mapper

import org.architecture.sport.domain.model.Room
import org.architecture.sport.server.entity.RoomEntity
import org.springframework.stereotype.Service

@Service
class RoomEntityMapper {

    fun toDomain(roomEntity: RoomEntity): Room {
        return Room(
            id = roomEntity.id,
            name = roomEntity.name,
            centerSportId = roomEntity.centerSportId,
            price = roomEntity.price,
            history = roomEntity.history
        )
    }

    fun toEntity(room: Room): RoomEntity {
        return RoomEntity(
            id = room.id,
            name = room.name,
            centerSportId = room.centerSportId,
            price = room.price,
            history = room.history
        )
    }
}