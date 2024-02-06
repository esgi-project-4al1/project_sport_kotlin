package org.architecture.sport.server.adapter

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.CenterSport
import org.architecture.sport.domain.ports.server.CenterSportPersistenceSpi
import org.architecture.sport.server.mapper.CenterSportEntityMapper
import org.architecture.sport.server.repository.CenterSportRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class CenterSportAdapter(
    private val centerSportRepository: CenterSportRepository,
    private val centerSportMapper: CenterSportEntityMapper,
) : CenterSportPersistenceSpi {
    override fun save(centerSport: CenterSport): Either<ApplicationError, CenterSport> {
        try {
            val centerSportEntity = centerSportMapper.toServer(centerSport)
            val result = centerSportRepository.save(centerSportEntity)
            return centerSportMapper.toDomain(result).right()
        } catch (e: Exception) {
            return ApplicationError(
                context = "CenterSport",
                message = "CenterSport is not saved",
                value = centerSport
            ).left()
        }
    }

    override fun findById(id: UUID): CenterSport? {
        return centerSportRepository.findById(id)
            .getOrNull()?.let { centerSportMapper.toDomain(it) }
    }

    override fun findAll(): List<CenterSport> {
        return centerSportRepository
            .findAll()
            .map { centerSportMapper.toDomain(it) }
    }
}