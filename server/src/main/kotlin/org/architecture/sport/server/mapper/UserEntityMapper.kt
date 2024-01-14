package org.architecture.sport.server.mapper

import org.architecture.sport.domain.model.User
import org.architecture.sport.server.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class UserEntityMapper {

    fun toServer(
        user: User
    ): UserEntity {
        return UserEntity(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            address = user.address,
            phoneNumber = user.phoneNumber,
            money = user.money
        )
    }

    fun toDomain(
        userEntity: UserEntity
    ): User {
        return User(
            id = userEntity.id,
            firstName = userEntity.firstName,
            lastName = userEntity.lastName,
            email = userEntity.email,
            address = userEntity.address,
            phoneNumber = userEntity.phoneNumber,
            money = userEntity.money,
        )
    }
}