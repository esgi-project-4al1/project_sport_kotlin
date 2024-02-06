package org.architecture.sport.client.dto

data class PrestationDto(
    val name: String,
    val centerSportId: String?,
    val price: Double,
    val description: String,
)
