package org.architecture.sport.domain.ports.server

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Enterprise
import java.util.UUID

interface EnterprisePersistenceSpi : PersistenceSpi<Enterprise, UUID> {

    fun addUsers(id: UUID, userId: UUID): Either<ApplicationError, Enterprise>

    fun removeUsers(enterpriseId: UUID, userId: UUID): Either<ApplicationError, Enterprise>

    fun isUserAlreadyInEnterprise(userId: UUID): Boolean

    fun isUserIsInEnterprise(enterpriseId: UUID, userId: UUID): Boolean

    fun get(id: UUID): Enterprise?

    fun getAll(): List<Enterprise>

    fun getEnterpriseByUserId(userId: UUID): Enterprise?
}