package org.architecture.sport.server.entity

import org.architecture.sport.domain.model.History
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("rooms")
data class RoomEntity(
    @Id
    val id: UUID,
    val name: String,
    val centerSportId: UUID,
    val price: Double = 0.0,
    val history: List<History> = emptyList()
)
