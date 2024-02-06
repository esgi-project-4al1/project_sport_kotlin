package org.architecture.sport.domain.ports.server

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Room
import java.util.UUID

interface RoomPersistenceSpi : PersistenceSpi<Room, UUID> {

    fun createRoom(
        room: Room
    ): Either<ApplicationError, Room>



    fun getRoom(
        id: UUID
    ): Room?


    fun getRooms(
        centerSportId: UUID? = null
    ): List<Room>
}