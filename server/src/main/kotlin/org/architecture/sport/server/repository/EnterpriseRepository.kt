package org.architecture.sport.server.repository

import org.architecture.sport.server.entity.EnterpriseEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EnterpriseRepository : MongoRepository<EnterpriseEntity, UUID> {

    @Query("{'listUserOfEnterprise': ?0}")
    fun findByListUserOfEnterpriseContaining(id: UUID): List<EnterpriseEntity>
}