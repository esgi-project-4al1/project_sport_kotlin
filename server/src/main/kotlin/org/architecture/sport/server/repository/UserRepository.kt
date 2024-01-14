package org.architecture.sport.server.repository

import org.architecture.sport.domain.model.User
import org.architecture.sport.server.entity.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: MongoRepository<UserEntity, UUID> {
}