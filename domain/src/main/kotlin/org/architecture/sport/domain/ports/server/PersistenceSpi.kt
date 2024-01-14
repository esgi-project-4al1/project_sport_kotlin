package org.architecture.sport.domain.ports.server

import arrow.core.Either
import arrow.core.Option
import org.architecture.sport.domain.error.ApplicationError

interface PersistenceSpi<T, ID> {
    fun save(o: T): Either<ApplicationError?, T>

    fun findById(id: ID): T?
}