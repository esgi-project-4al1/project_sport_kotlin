package org.architecture.sport.domain.ports.client

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Room
import java.util.*

interface RoomApi {

    fun createRoom(
        centerSportId: UUID,
        room: Room
    ): Either<ApplicationError, Room>


    fun getRoom(
        id: UUID?
    ): List<Room>

    fun getRoomByCenterSport(
        centerSportId: UUID
    ): List<Room>
    fun updatePrice(
        id: UUID,
        price: Double
    ): Either<ApplicationError, Room>
}