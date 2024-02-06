package org.architecture.sport.server.entity

import org.architecture.sport.domain.model.History
import org.architecture.sport.domain.model.TransactionStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "sessions")
data class SessionEntity(
    @Id
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
