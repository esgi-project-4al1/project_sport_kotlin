package org.architecture.sport.domain.ports.client

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Enterprise
import java.util.UUID

interface EnterpriseApi {

    fun create(enterprise: Enterprise): Either<ApplicationError, Enterprise>

    fun gets(id: UUID?): List<Enterprise>

    fun addUsers(enterpriseID: UUID, userId: UUID): Either<ApplicationError, Enterprise>


    fun removeUsers(id: UUID, userId: UUID): Either<ApplicationError, Enterprise>

}