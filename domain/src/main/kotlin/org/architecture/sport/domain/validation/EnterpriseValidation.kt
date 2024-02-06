package org.architecture.sport.domain.validation

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern
import org.architecture.sport.domain.model.Enterprise
import org.springframework.stereotype.Service

@Service
class EnterpriseValidation {

    fun validateEnterprise(enterprise: Enterprise): ValidationResult<Enterprise> {
        val validation = Validation<Enterprise>{
            Enterprise::name required {
                pattern("^[a-zA-Z]*\$")
                minLength(2)
                maxLength(100)
            }
        }
        return validation.validate(enterprise)
    }
}