package org.architecture.sport.server.entity

import org.architecture.sport.domain.model.Address
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "centers")
data class CenterSportEntity(
    @Id
    val id: UUID,
    val name: String,
    val address: Address,
)
