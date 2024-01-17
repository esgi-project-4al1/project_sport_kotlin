package org.architecture.sport.domain.functionnal

import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.generate.generateApplicationError
import org.architecture.sport.domain.generate.generateCenterSport
import org.architecture.sport.domain.model.CenterSport
import org.architecture.sport.domain.ports.server.CenterSportPersistenceSpi
import org.architecture.sport.domain.utils.toList
import org.architecture.sport.domain.validation.CenterSportValidation
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CenterSportServiceTest {

    private val centerSportValidation = CenterSportValidation()

    private val centerSportPersistenceSpi = mock<CenterSportPersistenceSpi> {  }

    private val centerSportService: CenterSportService = CenterSportService(
        centerSportValidation = centerSportValidation,
        centerSportPersistenceSpi = centerSportPersistenceSpi
    )

    @Test
    fun create_With_GoodInformation() {
        // Given

        val centerSport = generateCenterSport()
        val test = centerSportValidation.validateCenterSport(centerSport)
        // When
        whenever(centerSportPersistenceSpi.save(centerSport)).thenReturn(centerSport.right())
        // Then
        val result = centerSportService.create(centerSport)
        assertEquals(centerSport.right(), result)
    }


    @Test
    fun create_With_NotGoodInformation() {
        // Given
        val centerSport = generateCenterSport().copy(name = "a")
        val applicationError = generateApplicationError(
            context = "CenterSport",
            message = "CenterSport is not valid",
            value = centerSport
        ).left()
        // When
        whenever(centerSportPersistenceSpi.save(centerSport)).thenReturn(centerSport.right())
        // Then
        val result = centerSportService.create(centerSport)
        assertEquals(applicationError, result)
    }

    @Test
    fun gets_Exist() {
        // Given
        val centerSport = generateCenterSport()
        // When
        whenever(centerSportPersistenceSpi.findById(centerSport.id)).thenReturn(centerSport)
        // Then
        val result = centerSportService.gets(centerSport.id)
        assertEquals(centerSport.toList(), result)
    }

    @Test
    fun gets_NotExist() {
        // Given
        val centerSport = generateCenterSport()
        // When
        whenever(centerSportPersistenceSpi.findById(centerSport.id)).thenReturn(null)
        // Then
        val result = centerSportService.gets(centerSport.id)
        assertEquals(emptyList<CenterSport>(), result)
    }

    @Test
    fun gets_ListExistNotIDDone() {
        // Given
        val listCenterSport = listOf(
            generateCenterSport(),
            generateCenterSport(),
            generateCenterSport(),
        )
        // When
        whenever(centerSportPersistenceSpi.findAll()).thenReturn(listCenterSport)
        // Then
        val result = centerSportService.gets(null)
        assertEquals(listCenterSport, result)
    }
}