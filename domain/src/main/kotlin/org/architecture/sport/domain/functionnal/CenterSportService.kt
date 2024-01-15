package org.architecture.sport.domain.functionnal

import arrow.core.Either
import arrow.core.left
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.CenterSport
import org.architecture.sport.domain.ports.client.CenterSportApi
import org.architecture.sport.domain.ports.server.CenterSportPersistenceSpi
import org.architecture.sport.domain.utils.toList
import org.architecture.sport.domain.validation.CenterSportValidation
import org.springframework.stereotype.Service
import java.util.*

@Service
class CenterSportService(
    private val centerSportValidation: CenterSportValidation,
    private val centerSportPersistenceSpi: CenterSportPersistenceSpi,
) : CenterSportApi {
    override fun create(centerSport: CenterSport): Either<ApplicationError, CenterSport> {
        val validation = centerSportValidation.validateCenterSport(centerSport)
        if (validation.errors.isNotEmpty()) {
            return ApplicationError(
                context = "CenterSport",
                message = "CenterSport is not valid",
                value = centerSport
            ).left()
        }
        return centerSportPersistenceSpi.save(centerSport)

    }

    override fun gets(id: UUID?): List<CenterSport> {
        return if (id != null) {
            centerSportPersistenceSpi.findById(id).toList()
        } else {
            centerSportPersistenceSpi.findAll()
        }

    }
}