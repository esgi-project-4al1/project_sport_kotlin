package org.architecture.sport.client.dto

data class UserCreateDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val street: String,
    val city: String,
    val postalCode: String,
    val country: String,
    val money: Double,
)
