package org.architecture.sport.server.mapper

import org.architecture.sport.domain.model.Session
import org.architecture.sport.server.entity.SessionEntity
import org.springframework.stereotype.Service

@Service
class SessionEntityMapper {

    fun toDomain(
        sessionEntity: SessionEntity
    ): Session {
        return Session(
            id = sessionEntity.id,
            name = sessionEntity.name,
            centerSportId = sessionEntity.centerSportId,
            price = sessionEntity.price,
            description = sessionEntity.description,
            prestation = sessionEntity.prestation,
            material = sessionEntity.material,
            startDate = sessionEntity.startDate,
            endDate = sessionEntity.endDate,
            maxParticipants = sessionEntity.maxParticipants,
            minParticipants = sessionEntity.minParticipants,
            history = sessionEntity.history,
            participants = sessionEntity.participants,
        )
    }


    fun toEntity(
        session: Session
    ): SessionEntity {
        return SessionEntity(
            id = session.id,
            name = session.name,
            centerSportId = session.centerSportId,
            price = session.price,
            description = session.description,
            prestation = session.prestation,
            material = session.material,
            startDate = session.startDate,
            endDate = session.endDate,
            maxParticipants = session.maxParticipants,
            minParticipants = session.minParticipants,
            history = session.history,
            participants = session.participants,
        )
    }
}