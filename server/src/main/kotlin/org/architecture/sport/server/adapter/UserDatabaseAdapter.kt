package org.architecture.sport.server.adapter

import arrow.core.*
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.User
import org.architecture.sport.domain.ports.server.UserPersistenceSpi
import org.architecture.sport.server.mapper.UserEntityMapper
import org.architecture.sport.server.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserDatabaseAdapter(
    private val userRepository: UserRepository,
    private val userEntityMapper: UserEntityMapper
) : UserPersistenceSpi {
    override fun save(user: User): Either<ApplicationError, User> {
        try {
            val userEntity = userEntityMapper.toServer(user)
            val result = userRepository.save(userEntity)
            return userEntityMapper.toDomain(result).right()
        } catch (e: Exception) {
            return ApplicationError(
                context = "User",
                message = "User is not saved",
                value = user
            ).left()

        }
    }

    override fun findById(id: UUID): User? {
        return userRepository.findById(id).getOrNull()?.let { userEntityMapper.toDomain(it) }
    }

    override fun findAll(): List<User> {
        return  userRepository.findAll().stream().map { userEntityMapper.toDomain(it) }.toList()
    }
}