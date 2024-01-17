package org.architecture.sport.domain.functionnal

import arrow.core.left
import arrow.core.right
import org.architecture.sport.domain.generate.generateApplicationError
import org.architecture.sport.domain.generate.generateCenterSport
import org.architecture.sport.domain.generate.generateMaterial
import org.architecture.sport.domain.ports.server.CenterSportPersistenceSpi
import org.architecture.sport.domain.ports.server.MaterialPersistenceSpi
import org.architecture.sport.domain.utils.toList
import org.architecture.sport.domain.validation.MaterialValidation
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.UUID

class MaterialServiceTest {

    private val materialValidation = MaterialValidation()
    private val materialPersistenceSpi = mock<MaterialPersistenceSpi>{}
    private val centerSportPersistenceSpi = mock<CenterSportPersistenceSpi> {  }
    private val  materialService = MaterialService(
        materialValidation,
        materialPersistenceSpi,
        centerSportPersistenceSpi,
    )


    @Test
    fun createMaterial_withGoodValue() {
        //Given
        val generateMaterial = generateMaterial()
        materialValidation.validateMaterial(generateMaterial)
        //When
        whenever(materialPersistenceSpi.save(generateMaterial)).thenReturn(generateMaterial.right())
        // Then
        val result = materialService.createMaterial(generateMaterial)
        assertEquals(result, generateMaterial.right())
    }

    @Test
    fun createMaterial_withGoodNoValue() {
        //Given
        val generateMaterial = generateMaterial().copy(name = "a")
        val applicationError = generateApplicationError(
            context = "Material",
            message = "Material is not valid",
            value = generateMaterial
        ).left()
        // Then
        val result = materialService.createMaterial(generateMaterial)
        assertEquals(result, applicationError)
    }

    @Test
    fun getMaterial_Exits() {
        //Given
        val generateMaterial = generateMaterial()
        //When
        whenever(materialPersistenceSpi.findById(generateMaterial.id)).thenReturn(generateMaterial)
        //Then
        val result = materialService.getMaterial(generateMaterial.id)
        assertEquals(result, generateMaterial.toList())

    }

    @Test
    fun distributeMaterial() {
        //Given
        val materialId = UUID.randomUUID()
        val centerSportId = UUID.randomUUID()
        val generateCenterSport = generateCenterSport().copy(id = centerSportId)
        val generateMaterial = generateMaterial().copy(id = materialId)
        val saveMaterial = generateMaterial.copy(centerSportId = centerSportId)
        //When
        whenever(materialPersistenceSpi.findById(materialId)).thenReturn(generateMaterial)
        whenever(centerSportPersistenceSpi.findById(centerSportId)).thenReturn(generateCenterSport)
        whenever(materialPersistenceSpi.save(saveMaterial)).thenReturn(saveMaterial.right())
        //Then
        val result = materialService.distributeMaterial(materialId, centerSportId)
        assertEquals(saveMaterial.right(), result)

    }

}