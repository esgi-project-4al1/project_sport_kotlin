package org.architecture.sport.domain.validation

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.maximum
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.minimum
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.Session
import org.architecture.sport.domain.utils.intervalTime
import org.architecture.sport.domain.utils.notBeforeNow
import org.architecture.sport.domain.utils.openHour
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class SessionValidation {


    fun validateSession(session: Session): ValidationResult<Session> {
        val validation = Validation<Session> {
            Session::name required {
                minLength(2)
                maxLength(50)
            }
            Session::price required {
                minimum(0.0)
                maximum(1000.0)
            }
            Session::description required {
                minLength(2)
                maxLength(100)
            }
            Session::maxParticipants required {
                minimum(0)
                maximum(100)
            }
            Session::startDate required {
                openHour(8,20)
                notBeforeNow()
                intervalTime(session.endDate)
            }
            Session::endDate required {
                openHour(8,20)
                notBeforeNow()
                intervalTime(session.startDate)
            }

        }
        return validation.validate(session)
    }


    fun validationAddParticipant(session: Session): Either<ApplicationError, Boolean> {
        if (session.participants.size >= session.maxParticipants) {
            return ApplicationError(
                context = "Session",
                message = "Session is full",
                value = session
            ).left()
        }
        return true.right()
    }

    fun validationUnJoinParticipant(session: Session): Either<ApplicationError, Boolean> {
        if (Duration.between(session.startDate, session.endDate).toMinutes() < 20) {
            return ApplicationError(
                context = "Session",
                message = "Session is less than 1 hour",
                value = session
            ).left()
        }
        return true.right()
    }


}