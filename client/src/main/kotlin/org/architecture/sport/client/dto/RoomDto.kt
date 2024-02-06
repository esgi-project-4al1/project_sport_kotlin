package org.architecture.sport.client.dto

import java.util.*

data class RoomDto(
    val name: String,
    val centerSportId: UUID,
    val price: Double = 0.0,
)
