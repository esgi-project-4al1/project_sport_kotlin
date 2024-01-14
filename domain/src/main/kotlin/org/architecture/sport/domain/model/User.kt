package org.architecture.sport.domain.model

import java.util.UUID

data class User(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: Address,
    val phoneNumber: String,
    val money: Double,
)
