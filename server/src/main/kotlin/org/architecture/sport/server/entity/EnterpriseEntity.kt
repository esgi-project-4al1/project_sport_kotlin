package org.architecture.sport.server.entity

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.*

@Document("enterprise")
data class EnterpriseEntity(
    @MongoId
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val listUserOfEnterprise: List<UUID> = listOf(),
)
