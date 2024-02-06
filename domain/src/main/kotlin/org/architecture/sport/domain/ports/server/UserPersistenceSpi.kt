package org.architecture.sport.domain.ports.server

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.User
import java.util.*

interface UserPersistenceSpi : PersistenceSpi<User, UUID> {

    override fun save(user: User): Either<ApplicationError, User>


    override fun findById(id: UUID): User?

    fun findAll(): List<User>
}