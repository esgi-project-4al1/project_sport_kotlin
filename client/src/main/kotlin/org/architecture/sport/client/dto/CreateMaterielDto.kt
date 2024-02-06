package org.architecture.sport.client.dto

data class CreateMaterielDto(
    val name: String,
    val quantity: Int = 1,
    val caution: Double = 0.0,
)
