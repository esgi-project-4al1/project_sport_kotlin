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
                    minLength(2)
                    maxLength(100)
                }
                Address::city required {
                    minLength(2)
                    maxLength(100)
                }
                Address::postalCode required {
                    minLength(2)
                    maxLength(100)
                }

                Address::country required {
                    minLength(2)
                    maxLength(100)
                }

            }
            CenterSport::name required {
                minLength(2)
                maxLength(100)
            }

        }
        return validationResult.validate(centerSport)
    }
}