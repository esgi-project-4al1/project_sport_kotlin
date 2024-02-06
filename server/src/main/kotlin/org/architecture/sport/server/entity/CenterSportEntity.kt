package org.architecture.sport.server.entity

import org.architecture.sport.domain.model.Address
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.*

@Document(collection = "centers")
data class CenterSportEntity(
    @MongoId
    val id: UUID,
    val name: String,
    val address: Address,
)
