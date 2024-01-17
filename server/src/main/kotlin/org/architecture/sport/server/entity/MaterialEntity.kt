package org.architecture.sport.server.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "material")
data class MaterialEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val quantity: Int = 1,
    val centerSportId: UUID? = null,
    val caution: Double = 0.0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val listMaintenanceTime: List<LocalDateTime> = emptyList(),
)
