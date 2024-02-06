package org.architecture.sport.domain.ports.client

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Prestation
import java.util.UUID

interface PresentationApi {


    fun createPresentation(
        presentation: Prestation,
        centerSportId: UUID,
        ): Either<ApplicationError, Prestation>


    fun getPresentation(
        presentationId: UUID?,
        ): List<Prestation>

    fun getPresentationsByCenterSport(
        centerSportId: UUID,
        ): List<Prestation>

    fun updatePresentation(
        presentationId: UUID,
        newPrice: Double,
        ): Either<ApplicationError, Prestation>
}