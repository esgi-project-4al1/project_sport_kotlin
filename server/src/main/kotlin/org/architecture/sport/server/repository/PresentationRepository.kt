package org.architecture.sport.server.repository

import org.architecture.sport.server.entity.PresentationEntity
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface PresentationRepository : MongoRepository<PresentationEntity, UUID> {

    fun findByCenterSportId(centerSportId: UUID): List<PresentationEntity>
}