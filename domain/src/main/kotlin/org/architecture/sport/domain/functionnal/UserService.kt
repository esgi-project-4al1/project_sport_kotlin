package org.architecture.sport.domain.functionnal

import arrow.core.Either
import arrow.core.left
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.User
import org.architecture.sport.domain.model.UserMoney
import org.architecture.sport.domain.ports.client.UserApi
import org.architecture.sport.domain.ports.server.UserPersistenceSpi
import org.architecture.sport.domain.validation.UserValidation
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userValidation: UserValidation,
    private val userPersistenceSpi: UserPersistenceSpi,
) : UserApi {
    override fun create(user: User): Either<ApplicationError, User> {
        val validation = userValidation.validateUser(user)
        if (validation.errors.isNotEmpty()) {
            return ApplicationError(
                context = "User",
                message = "User is not valid",
                value = user
            ).left()
        }
        return userPersistenceSpi.save(user)
    }

    override fun updateMoney(userMoney: UserMoney): Either<ApplicationError, User> {
        val validation = userValidation.validateUserMoney(userMoney)
        if (validation.errors.isNotEmpty()) {
            return ApplicationError(
                context = "User",
                message = "User is not valid",
                value = userMoney
            ).left()
        }
        val user = userPersistenceSpi.findById(userMoney.id) ?: return ApplicationError(
            context = "User",
            message = "User is not found",
            value = userMoney
        ).left()
        return userPersistenceSpi.save(user.copy(money = userMoney.money+ user.money))
    }
}