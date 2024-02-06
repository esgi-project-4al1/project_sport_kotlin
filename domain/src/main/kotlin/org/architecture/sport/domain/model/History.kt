package org.architecture.sport.domain.model

import java.time.LocalDateTime
import java.util.*

data class History(
    val id: UUID= UUID.randomUUID(),
    val date: LocalDateTime,
    val action: String
)
