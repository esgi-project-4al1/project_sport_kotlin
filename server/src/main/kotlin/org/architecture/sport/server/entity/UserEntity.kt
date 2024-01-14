package org.architecture.sport.server.entity

import org.architecture.sport.domain.model.Address
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "users")
data class UserEntity(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: Address,
    val phoneNumber: String,
    val money: Double,
)
