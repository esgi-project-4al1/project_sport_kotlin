package org.architecture.sport.domain.validation

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.*
import org.architecture.sport.domain.model.Address
import org.architecture.sport.domain.model.User
import org.architecture.sport.domain.model.UserMoney
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class UserValidation {


    fun validateUser(user: User): ValidationResult<User> {
        val validateUser = Validation<User> {
            User::email required {
                pattern("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
            }
            User::firstName required {
                minLength(2)
                maxLength(100)
            }
            User::lastName required {
                minLength(2)
                maxLength(100)
            }
            User::phoneNumber required {
                pattern("^[0-9]*\$")
                minLength(2)
                maxLength(100)
            }

            User::money required {
                minimum(0.0)
                maximum(100000.0)
            }
            User::address required {
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

        }
        return validateUser(user)
    }


    fun validateUserMoney(userMoney: UserMoney): ValidationResult<UserMoney> {
        val validateUserMoney = Validation<UserMoney> {
            UserMoney::money required {
                minimum(0.0)
                maximum(100000.0)
            }
        }
        return validateUserMoney(userMoney)
    }
}