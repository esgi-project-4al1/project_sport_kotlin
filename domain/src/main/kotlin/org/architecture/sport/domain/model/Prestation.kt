package org.architecture.sport.domain.model

import java.util.*

data class Prestation(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val centerSportId: UUID,
    val price: Double = 0.0,
    val description: String,
    val history: List<History> = emptyList()
)
