package org.architecture.sport.domain.functionnal

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Room
import org.architecture.sport.domain.ports.client.RoomApi
import org.architecture.sport.domain.ports.server.RoomPersistenceSpi
import org.architecture.sport.domain.validation.RoomValidation
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoomService(
    private val roomValidation: RoomValidation,
    private val roomPersistenceSpi: RoomPersistenceSpi,
) : RoomApi {
    override fun createRoom(centerSportId: UUID, room: Room): Either<ApplicationError, Room> {
        val validation = roomValidation.validateRoom(room)
        return if (validation.errors.isEmpty()) {
            try {
                roomPersistenceSpi.createRoom(room.copy(centerSportId = centerSportId)).right()
            } catch (e: Exception) {
                ApplicationError(
                    context = "RoomService.createRoom",
                    message = "Error when creating room",
                    value = room
                ).left()
            }
        } else {
            ApplicationError(
                context = "RoomService.createRoom",
                message = "Room is not valid",
                value = room
            ).left()
        }
    }

    override fun getRoom(id: UUID?): List<Room> {
        return if (id != null) {
            listOfNotNull(roomPersistenceSpi.getRoom(id))
        } else {
            roomPersistenceSpi.getRooms()
        }
    }

    override fun getRoomByCenterSport(centerSportId: UUID): List<Room> {
        return roomPersistenceSpi.getRooms(centerSportId)
    }

    override fun updatePrice(id: UUID, price: Double): Either<ApplicationError, Room> {
        val findRoom = roomPersistenceSpi.getRoom(id) ?: return ApplicationError(
            context = "RoomService.updatePrice",
            message = "Room not found",
            value = id
        ).left()
        return try {
            roomPersistenceSpi.save(findRoom.copy(price = price))
        } catch (e: Exception) {
            ApplicationError(
                context = "RoomService.updatePrice",
                message = "Error when updating price",
                value = id
            ).left()
        }
    }


}