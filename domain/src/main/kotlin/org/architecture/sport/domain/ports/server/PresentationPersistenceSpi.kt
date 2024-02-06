package org.architecture.sport.domain.ports.server

import arrow.core.Either
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Prestation
import java.util.UUID

interface PresentationPersistenceSpi : PersistenceSpi<Prestation, UUID> {

    fun getPresentationsByCenterSport(centerSportId: UUID): List<Prestation>


    fun save(presentation: Prestation, centerSportId: UUID): Either<ApplicationError, Prestation>

    fun getPresentation(presentationId: UUID): Prestation?

    fun getPresentations(): List<Prestation>
    fun isGoodCenterSport(centerSportId: UUID, presentationId: UUID): Boolean


}