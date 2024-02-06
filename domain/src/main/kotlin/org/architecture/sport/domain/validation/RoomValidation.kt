package org.architecture.sport.domain.validation

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.*
import org.architecture.sport.domain.model.Room
import org.springframework.stereotype.Service

@Service
class RoomValidation {

    fun validateRoom(
        room: Room
    ): ValidationResult<Room> {
        val validation = Validation<Room> {
            Room::name required {
                pattern("^[a-zA-Z0-9 ]*\$")
                minLength(2)
                maxLength(100)
            }
            Room::price required {
                minimum(1.0)
                maximum(1000.0)
            }
        }
        return validation.validate(room)
    }
}