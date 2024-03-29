package org.architecture.sport.client.ressource

import org.architecture.sport.client.dto.CreateMaterielDto
import org.architecture.sport.client.mapping.MaterialMapper
import org.architecture.sport.client.utils.tryCatchUUID
import org.architecture.sport.domain.ports.client.MaterialApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/material")
class MaterialController(
    private val materialMapper: MaterialMapper,
    private val materialApi: MaterialApi
) {


    @PostMapping
    fun createMaterial(
        @RequestBody(required = true) createMaterielDto: CreateMaterielDto,
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


    @GetMapping
    fun getMaterial(
        @RequestParam(required = false) materialId: String?,
    ): ResponseEntity<Any> {
        if (materialId != null) {
            tryCatchUUID(materialId) ?: return ResponseEntity.badRequest().body("Invalid UUID")
        }
        val material = materialApi.getMaterial(
            id = materialId?.let { UUID.fromString(materialId) }
        )
        return ResponseEntity.ok(material)
    }


    @PostMapping("/distribute")
    fun distributeMaterial(
        @RequestParam(required = true) materialId: String,
        @RequestParam(required = true) centerSportId: String,
    ): ResponseEntity<Any> {
        tryCatchUUID(materialId) ?: return ResponseEntity.badRequest().body("Invalid UUID materialId")
        tryCatchUUID(centerSportId) ?: ResponseEntity.badRequest().body("Invalid UUID centerSportId")

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

    @PostMapping("/maintenance")
    fun maintenanceMaterial(
        @RequestParam(required = false) materialId: String,
    ): ResponseEntity<Any> {
        tryCatchUUID(materialId) ?: ResponseEntity.badRequest().body("Invalid UUID materialId")
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