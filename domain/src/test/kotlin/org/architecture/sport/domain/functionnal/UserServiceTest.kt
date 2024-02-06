package org.architecture.sport.domain.functionnal

import arrow.core.left
import arrow.core.right
import io.github.serpro69.kfaker.Faker
import org.architecture.sport.domain.generate.generateApplicationError
import org.architecture.sport.domain.generate.generateUser
import org.architecture.sport.domain.generate.generateUserMoney
import org.architecture.sport.domain.model.User
import org.architecture.sport.domain.ports.server.UserPersistenceSpi
import org.architecture.sport.domain.validation.UserValidation
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class UserServiceTest{

    private val userValidation = UserValidation()
    private val userPersistenceSpi = mock<UserPersistenceSpi>()
    private val userService = UserService(
        userValidation = userValidation,
        userPersistenceSpi = userPersistenceSpi
    )

    @Test
    fun create_with_goodUser() {
        // Given
        val generateUser = generateUser()
        // When
        userValidation.validateUser(generateUser)
        whenever(userPersistenceSpi.save(generateUser)).thenReturn(generateUser.right())
        // Then
        val user = userService.create(generateUser)
        assertEquals(generateUser.right(), user)
    }

    @Test
    fun create_with_notGoodUser() {
        // Given
        val generateUser = generateUser().copy(firstName = "a")
        val generateError = generateApplicationError(value = generateUser).left()
        // When
        userValidation.validateUser(generateUser)
        whenever(userPersistenceSpi.save(generateUser)).thenReturn(generateUser.right())
        // Then
        val user = userService.create(generateUser)
        assertEquals(generateError, user)
    }

    @Test
    fun updateMoney_withGoodUserAndExist() {
        // Given
        val generateUser = generateUser()
        val money = 100.0
        val generateUserMoney = generateUserMoney(id = generateUser.id, money)
        val userExcepted = generateUser.copy(money = money).right()
        // When
        whenever(userPersistenceSpi.findById(generateUserMoney.id)).thenReturn(generateUser)
        whenever(userPersistenceSpi.save(generateUser.copy(money =  money))).thenReturn(generateUser.copy(money = money).right())
        // Then
        val user = userService.updateMoney(generateUserMoney)
        assertEquals(userExcepted, user)
    }


    @Test
    fun updateMoney_withGoodUserAndButNotExist() {
        // Given
        val generateUser = generateUser()
        val money = 100.0

        val generateUserMoney = generateUserMoney(id = generateUser.id, money)
        val applicationError = generateApplicationError(
            context = "User",
            message = "User is not found",
            value = generateUserMoney
        ).left(
        )
        // When
        whenever(userPersistenceSpi.findById(generateUserMoney.id)).thenReturn(null)
        // Then
        val user = userService.updateMoney(generateUserMoney)
        assertEquals(applicationError, user)
    }

    @Test
    fun updateMoney_withNotGoodUserMoney() {
        // Given
        val generateUser = generateUser()
        val generateUserMoney = generateUserMoney(id = generateUser.id, money = -1.0)
        val applicationError = generateApplicationError(
            context = "User",
            message = "User is not valid",
            value = generateUserMoney
        ).left()
        // When
        // Then
        val user = userService.updateMoney(generateUserMoney)
        assertEquals(applicationError, user)
    }




}