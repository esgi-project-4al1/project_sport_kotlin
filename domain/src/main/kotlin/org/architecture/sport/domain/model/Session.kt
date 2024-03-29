package org.architecture.sport.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Session(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val centerSportId: UUID,
    val price: Double,
    val description: String,
    val prestation: UUID?,
    val material: UUID?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val maxParticipants: Int,
    val minParticipants: Int,
    val history: List<History> = emptyList(),
    val participants: List<Pair<UUID, TransactionStatus>> = emptyList(),
)
