package org.architecture.sport.server.repository

import org.architecture.sport.server.entity.CenterSportEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CenterSportRepository: MongoRepository<CenterSportEntity, UUID> {
}