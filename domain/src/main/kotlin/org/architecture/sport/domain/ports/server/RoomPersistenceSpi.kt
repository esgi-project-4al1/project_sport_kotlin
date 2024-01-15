package org.architecture.sport.domain.ports.server

import org.architecture.sport.domain.model.Room
import java.util.UUID

interface RoomPersistenceSpi : PersistenceSpi<Room, UUID> {

    fun createRoom(
        room: Room
    ): Room



    fun getRoom(
        id: UUID
    ): Room?


    fun getRooms(
        centerSportId: UUID? = null
    ): List<Room>
}