package org.architecture.sport.domain.ports.server

import arrow.core.Either
import arrow.core.Option
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.User
import org.architecture.sport.domain.model.UserMoney
import java.util.UUID

interface UserPersistenceSpi: PersistenceSpi<User, UUID> {

    override fun save(user: User): Either<ApplicationError, User>


     override fun findById(id: UUID): User?


}