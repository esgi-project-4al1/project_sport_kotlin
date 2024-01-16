package org.architecture.sport.domain.functionnal

import arrow.core.Either
import arrow.core.left
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Enterprise
import org.architecture.sport.domain.ports.client.EnterpriseApi
import org.architecture.sport.domain.ports.server.EnterprisePersistenceSpi
import org.architecture.sport.domain.ports.server.UserPersistenceSpi
import org.architecture.sport.domain.utils.toList
import org.architecture.sport.domain.validation.EnterpriseValidation
import org.springframework.stereotype.Service
import java.util.*

@Service
class EnterpriseService(
    private val enterpriseValidation: EnterpriseValidation,
    private val enterprisePersistenceSpi: EnterprisePersistenceSpi,
    private val userPersistenceSpi: UserPersistenceSpi,
): EnterpriseApi {
    override fun create(enterprise: Enterprise): Either<ApplicationError, Enterprise> {
        val validation = enterpriseValidation.validateEnterprise(enterprise)
        return if (validation.errors.isEmpty()) {
            enterprisePersistenceSpi.save(enterprise)
        } else {
            ApplicationError(
                context = "Enterprise",
                message = "Enterprise not valid",
                value = enterprise
            ).left()
        }
    }

    override fun gets(id: UUID?): List<Enterprise> {
        return if (id != null) {
            enterprisePersistenceSpi.get(id).toList()
        } else {
            enterprisePersistenceSpi.getAll()
        }
    }

    override fun addUsers(enterpriseID: UUID, userId: UUID): Either<ApplicationError, Enterprise> {
        val enterprise = enterprisePersistenceSpi.get(enterpriseID)
        val user = userPersistenceSpi.findById(userId)
        if (enterprise != null && user != null) {
            if (enterprisePersistenceSpi.isUserAlreadyInEnterprise(enterpriseID, userId)) {
                return ApplicationError(
                    context = "Enterprise",
                    message = "User already in enterprise",
                    value = enterprise
                ).left()
            }
        } else {
            return ApplicationError(
                context = "Enterprise",
                message = "Enterprise or user not found",
                value = enterprise
            ).left()
        }
        return enterprisePersistenceSpi.addUsers(enterpriseID, userId)
    }

    override fun removeUsers(id: UUID, userId: UUID): Either<ApplicationError, Enterprise> {
        val enterprise = enterprisePersistenceSpi.get(id)
        val user = userPersistenceSpi.findById(userId)
        if (enterprise != null && user != null) {
            if (!enterprisePersistenceSpi.isUserIsInEnterprise(id, userId)) {
                return ApplicationError(
                    context = "Enterprise",
                    message = "User not in enterprise",
                    value = enterprise
                ).left()
            }
        } else {
            return ApplicationError(
                context = "Enterprise",
                message = "Enterprise or user not found",
                value = enterprise
            ).left()
        }
        return enterprisePersistenceSpi.removeUsers(id, userId)
    }

}