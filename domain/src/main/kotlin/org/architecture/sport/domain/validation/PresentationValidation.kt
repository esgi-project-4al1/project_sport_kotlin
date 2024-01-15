package org.architecture.sport.domain.validation

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.*
import org.architecture.sport.domain.model.Prestation
import org.springframework.stereotype.Service

@Service
class PresentationValidation {


    fun validatePresentation(presentation: Prestation): ValidationResult<Prestation>{
        val validation = Validation<Prestation> {
            Prestation::name required {
                pattern("^[a-zA-Z]+\$")
                minLength(2)
                maxLength(50)
            }
            Prestation::price required {
                minimum(0.0)
                maximum(1000.0)
            }
            Prestation::description required {
                pattern("^[a-zA-Z0-9 ]+\$")
                minLength(2)
                maxLength(100)
            }
        }
        return validation.validate(presentation)
    }



}