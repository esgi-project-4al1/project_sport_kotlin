package org.architecture.sport.client.dto

import java.time.LocalDateTime

data class SessionDto(
    val name: String,
    val centerSportId: String,
    val price: Double,
    val description: String,
    val prestation: String?,
    val material: String?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val maxParticipants: Int,
    val minParticipants: Int,
)
