package org.architecture.sport.server.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("enterprise")
data class EnterpriseEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val listUserOfEnterprise: List<UUID> = listOf(),
)
