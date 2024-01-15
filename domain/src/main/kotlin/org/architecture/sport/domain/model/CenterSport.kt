package org.architecture.sport.domain.model

import java.util.UUID

data class CenterSport(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val address: Address,
)
