package org.architecture.sport.domain.functionnal

import arrow.core.Either
import arrow.core.left
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Prestation
import org.architecture.sport.domain.ports.client.PresentationApi
import org.architecture.sport.domain.ports.server.PresentationPersistenceSpi
import org.architecture.sport.domain.utils.toList
import org.architecture.sport.domain.validation.PresentationValidation
import org.springframework.stereotype.Service
import java.util.*

@Service
class PresentationService(
    private val presentationPersistenceSpi: PresentationPersistenceSpi,
    private val presentationValidation: PresentationValidation
) : PresentationApi {
    override fun createPresentation(
        presentation: Prestation,
        centerSportId: UUID
    ): Either<ApplicationError, Prestation> {
        val validation = presentationValidation.validatePresentation(presentation)
        return if (validation.errors.isEmpty()) {
            presentationPersistenceSpi.save(presentation, centerSportId)
        } else {
            ApplicationError(
                context = "Presentation",
                message = "Presentation not valid",
                value = presentation
            ).left()
        }
    }

    override fun getPresentation(presentationId: UUID?): List<Prestation> {
        return if (presentationId != null) {
            presentationPersistenceSpi.getPresentation(presentationId).toList()
        } else {
            presentationPersistenceSpi.getPresentations()
        }
    }

    override fun getPresentationsByCenterSport(centerSportId: UUID): List<Prestation> {
        return presentationPersistenceSpi.getPresentationsByCenterSport(centerSportId)
    }

    override fun updatePresentation(presentationId: UUID, newPrice: Double): Either<ApplicationError, Prestation> {
        val presentation = presentationPersistenceSpi.getPresentation(presentationId)
        return if (presentation != null) {
            val newPresentation = presentation.copy(price = newPrice)
            presentationPersistenceSpi.save(newPresentation)
        } else {
            ApplicationError(
                context = "Presentation",
                message = "Presentation not found",
                value = presentationId
            ).left()
        }
    }
}