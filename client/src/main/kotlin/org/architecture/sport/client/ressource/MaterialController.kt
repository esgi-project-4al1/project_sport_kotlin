package org.architecture.sport.client.ressource

import org.architecture.sport.client.dto.CreateMaterielDto
import org.architecture.sport.client.mapping.MaterialMapper
import org.architecture.sport.client.utils.tryCatchUUID
import org.architecture.sport.domain.ports.client.MaterialApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/material")
class MaterialController(
    private val materialMapper: MaterialMapper,
    private val materialApi: MaterialApi
) {


    @PostMapping
    fun createMaterial(
        createMaterielDto: CreateMaterielDto,
    ): ResponseEntity<Any> {
        materialMapper.createMaterialToDomain(createMaterielDto).let {
            return materialApi.createMaterial(
                material = it
            ).fold(
                { error ->
                    ResponseEntity.badRequest().body(error.message)
                },
                { material ->
                    ResponseEntity.ok(material)
                }
            )
        }
    }


    @GetMapping(
        params = ["materialId"]
    )
    fun getMaterial(
        materialId: String?,
    ): ResponseEntity<Any> {
        if (materialId != null) {
            tryCatchUUID(materialId)?.let {
                return ResponseEntity.badRequest().body("Invalid UUID")
            }
        }
        val material = materialApi.getMaterial(
            id = UUID.fromString(materialId)
        )
        return ResponseEntity.ok(material)
    }


    @PostMapping
    fun distributeMaterial(
        materialId: String?,
        centerSportId: String?,
    ): ResponseEntity<Any>  {
        if (materialId != null) {
            tryCatchUUID(materialId)?.let {
                ResponseEntity.badRequest().body("Invalid UUID materialId")
            }
        }
        if (centerSportId != null) {
            tryCatchUUID(centerSportId)?.let {
                ResponseEntity.badRequest().body("Invalid UUID centerSportId")
            }
        }

        return materialApi.distributeMaterial(
            id = UUID.fromString(materialId),
            centerSportId = UUID.fromString(centerSportId)
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { material ->
                ResponseEntity.ok(material)
            }
        )
    }

    @PostMapping
    fun maintenanceMaterial(
        materialId: String?,
    ): ResponseEntity<Any>  {
        if (materialId != null) {
            tryCatchUUID(materialId)?.let {
                ResponseEntity.badRequest().body("Invalid UUID materialId")
            }
        }
        return materialApi.maintenanceMaterial(
            id = UUID.fromString(materialId)
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { material ->
                ResponseEntity.ok(material)
            }
        )
    }
}