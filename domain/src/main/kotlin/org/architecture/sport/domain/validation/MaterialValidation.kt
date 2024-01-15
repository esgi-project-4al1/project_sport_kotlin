package org.architecture.sport.domain.validation

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.*
import org.architecture.sport.domain.model.Material
import org.springframework.stereotype.Service

@Service
class MaterialValidation {

    fun validateMaterial(
        material: Material
    ): ValidationResult<Material> {
        val validation = Validation<Material> {
            Material::name required {
                pattern("^[a-zA-Z0-9]+\$")
                minLength(2)
                maxLength(100)
            }
            Material::quantity required {
                minimum(1)
                maximum(1000)
            }
            Material::price required {
                minimum(1.0)
                maximum(1000.0)
            }
        }
        return validation.validate(material)
    }
}