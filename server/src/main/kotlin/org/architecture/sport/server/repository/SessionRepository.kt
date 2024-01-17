package org.architecture.sport.server.repository

import org.architecture.sport.server.entity.SessionEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface SessionRepository: MongoRepository<SessionEntity, UUID> {

    fun findAllByCenterSportId(centerSportId: UUID): List<SessionEntity>

    @Query("{ \$or: [ { startDate: { \$gte: ?0, \$lte: ?1 } }, { endDate: { \$gte: ?0, \$lte: ?1 } } ] }")
    fun findOverlappingSessions(startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<SessionEntity>

    @Query("{ \$or: [ { startDate: { \$gte: ?0, \$lte: ?1 } }, { endDate: { \$gte: ?0, \$lte: ?1 } } ]}")
    fun findOverlappingSessionsByMaterial(startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<SessionEntity>
}