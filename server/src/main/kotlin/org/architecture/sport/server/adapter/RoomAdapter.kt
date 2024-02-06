package org.architecture.sport.server.adapter

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Room
import org.architecture.sport.domain.ports.server.RoomPersistenceSpi
import org.architecture.sport.server.mapper.RoomEntityMapper
import org.architecture.sport.server.repository.RoomRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoomAdapter(
    private val roomRepository: RoomRepository,
    private val roomMapper: RoomEntityMapper,
) : RoomPersistenceSpi {
    override fun createRoom(room: Room): Either<ApplicationError, Room>{
        try {
            val roomEntity = roomMapper.toEntity(room)
            val saveRoom = roomMapper.toDomain(roomRepository.save(roomEntity))
            return saveRoom.right()
        } catch (e: Exception) {
            return ApplicationError(
                "Error when saving room",
                "Error when saving room"
            ).left()
        }
    }

    override fun getRoom(id: UUID): Room? {
        return roomRepository.findById(id).map { roomMapper.toDomain(it) }.orElse(null)
    }

    override fun getRooms(centerSportId: UUID?): List<Room> {
        return if (centerSportId != null) {
            roomRepository.findAllByCenterSportId(centerSportId).stream().map { roomMapper.toDomain(it) }.toList()
        } else {
            roomRepository.findAll().stream().map { roomMapper.toDomain(it) }.toList()
        }
    }

    override fun save(o: Room): Either<ApplicationError, Room> {
        try {
            val roomEntity = roomMapper.toEntity(o)
            val saveRoom = roomMapper.toDomain(roomRepository.save(roomEntity)).right()
            return saveRoom
        } catch (e: Exception) {
            return ApplicationError(
                "Error when saving room",
                "Error when saving room"
            ).left()
        }
    }

    override fun findById(id: UUID): Room? {
        return roomRepository.findById(id).map { roomMapper.toDomain(it) }.orElse(null)
    }
}