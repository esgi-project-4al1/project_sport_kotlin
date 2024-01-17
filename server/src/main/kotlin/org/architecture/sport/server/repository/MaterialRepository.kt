package org.architecture.sport.server.repository

import org.architecture.sport.server.entity.MaterialEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MaterialRepository : MongoRepository<MaterialEntity, UUID>{
}