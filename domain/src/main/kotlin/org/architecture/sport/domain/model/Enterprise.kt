package org.architecture.sport.domain.model

import java.util.*

data class Enterprise(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val listUserOfEnterprise: List<UUID> = listOf(),
)