package org.architecture.sport.domain.ports.client

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.CenterSport
import java.util.UUID

interface CenterSportApi {

    fun create(
        centerSport: CenterSport
    ): Either<ApplicationError, CenterSport>


    fun gets(
        id: UUID?
    ):  List<CenterSport>
}