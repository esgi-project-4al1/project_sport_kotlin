package org.architecture.sport.server.adapter

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Prestation
import org.architecture.sport.domain.ports.client.PresentationApi
import org.architecture.sport.domain.ports.server.PresentationPersistenceSpi
import org.architecture.sport.server.mapper.PrestationEntityMapper
import org.architecture.sport.server.repository.PresentationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PrestationAdapter(
    private val prestationRepository: PresentationRepository,
    private val prestationMapper: PrestationEntityMapper,
): PresentationPersistenceSpi {

    override fun getPresentationsByCenterSport(centerSportId: UUID): List<Prestation> {
        return prestationRepository.findByCenterSportId(centerSportId).map { prestationMapper.toDomain(it) }
    }

    override fun save(presentation: Prestation, centerSportId: UUID): Either<ApplicationError, Prestation> {
        return try {
            val prestationEntity = prestationMapper.toEntity(presentation)
            prestationMapper.toDomain(prestationRepository.save(prestationEntity)).right()
        } catch (e: Exception) {
            ApplicationError(
                "Error when saving prestation",
                "Error when saving prestation"
            ).left()

        }

    }

    override fun save(o: Prestation): Either<ApplicationError, Prestation> {
        return try {
            val prestationEntity = prestationMapper.toEntity(o)
            prestationMapper.toDomain(prestationRepository.save(prestationEntity)).right()
        } catch (e: Exception) {
            ApplicationError(
                "Error when saving prestation",
                "Error when saving prestation"
            ).left()

        }
    }

    override fun getPresentation(presentationId: UUID): Prestation? {
        return prestationRepository.findById(presentationId).map { prestationMapper.toDomain(it) }.orElse(null)
    }

    override fun getPresentations(): List<Prestation> {
        return prestationRepository.findAll().stream().map { prestationMapper.toDomain(it) }.toList()
    }

    override fun isGoodCenterSport(centerSportId: UUID, presentationId: UUID): Boolean {
        val result = prestationRepository.findById(presentationId)
        return if (result.isPresent) {
            val prestation = result.get()
            prestation.centerSportId == centerSportId
        } else {
            false
        }
    }

    override fun findById(id: UUID): Prestation? {
        return prestationRepository.findById(id).map { prestationMapper.toDomain(it) }.orElse(null)
    }
}