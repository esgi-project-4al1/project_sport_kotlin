package org.architecture.sport.domain.ports.client

import arrow.core.Either
import org.architecture.sport.domain.model.User
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.UserMoney
import java.util.UUID

interface UserApi {

    fun create(
        user: User
    ): Either<ApplicationError, User>


    fun updateMoney(
        userMoney: UserMoney
    ): Either<ApplicationError, User>


    fun getUsers(
        id: UUID?,
    ): Either<ApplicationError, List<User>>
}