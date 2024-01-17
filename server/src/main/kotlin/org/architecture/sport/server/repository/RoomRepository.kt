package org.architecture.sport.server.repository

import org.architecture.sport.server.entity.RoomEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomRepository: MongoRepository<RoomEntity, UUID> {

    fun findAllByCenterSportId(centerSportId: UUID): List<RoomEntity>
}