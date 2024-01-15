package org.architecture.sport.domain.validation

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern
import org.architecture.sport.domain.model.Address
import org.architecture.sport.domain.model.CenterSport
import org.springframework.stereotype.Service

@Service
class CenterSportValidation {


    fun validateCenterSport(
        centerSport: CenterSport
    ): ValidationResult<CenterSport> {
        val validationResult = Validation<CenterSport> {
            CenterSport::address required {
                Address::street required {
                    pattern("^[a-zA-Z0-9 ]+\$")
                    minLength(2)
                    maxLength(100)
                }
                Address::city required {
                    pattern("^[a-zA-Z]+\$")
                    minLength(2)
                    maxLength(100)
                }
                Address::postalCode required {
                    pattern("^[0-9]{5}\$")
                }

                Address::country required {
                    pattern("^[a-zA-Z]+\$")
                    minLength(2)
                    maxLength(100)
                }
            }
            CenterSport::name required {
                pattern("^[a-zA-Z]+\$")
                minLength(2)
                maxLength(100)
            }

        }
        return validationResult.validate(centerSport)
    }
}