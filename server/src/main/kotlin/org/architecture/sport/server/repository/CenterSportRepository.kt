package org.architecture.sport.server.repository

import org.architecture.sport.server.entity.CenterSportEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CenterSportRepository: MongoRepository<CenterSportEntity, UUID> {

    override fun findById(id: UUID): Optional<CenterSportEntity>
    fun save(entity: CenterSportEntity): CenterSportEntity
    override fun deleteById(id: UUID)
}