package org.architecture.sport.domain.model

import java.time.LocalDateTime
import java.util.*
import kotlin.collections.List

data class Material(
    val id: UUID = UUID.randomUUID(),
    val name: String ,
    val quantity: Int = 1,
    val centerSportId: UUID? = null,
    val price: Double = 0.0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val listMaintenanceTime: List<LocalDateTime> = emptyList(),
)
