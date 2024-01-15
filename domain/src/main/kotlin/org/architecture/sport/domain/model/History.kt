package org.architecture.sport.domain.model

import java.util.*

data class History(
    val id: UUID,
    val date: Date,
    val action: String
)
