package org.architecture.sport.client.ressource

import org.architecture.sport.client.dto.PrestationDto
import org.architecture.sport.client.mapping.PrestationMapper
import org.architecture.sport.client.utils.tryCatchUUID
import org.architecture.sport.domain.ports.client.PresentationApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/prestation")
@RestController
class PresentationResource(
    private val presentationMapper: PrestationMapper,
    private val presentationApi: PresentationApi,
) {

    @PostMapping
    fun createPrestation(
        @RequestBody(required = true) presentationDto: PrestationDto,
        @RequestParam(required = true)  centerSportId: String,
    ): ResponseEntity<Any> {
        tryCatchUUID(centerSportId) ?: return ResponseEntity.badRequest().body("Invalid UUID for centerSportId")
        return presentationApi.createPresentation(
            presentation = presentationMapper.toDomain(presentationDto, centerSportId),
            centerSportId = UUID.fromString(centerSportId)
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { prestation ->
                ResponseEntity.ok(prestation)
            }
        )
    }


    @GetMapping
    fun getPrestation(
        @RequestParam(required = false)  prestationId: String?,
    ): ResponseEntity<Any> {
        if (prestationId != null){
            tryCatchUUID(prestationId) ?: return ResponseEntity.badRequest().body("Invalid UUID")
        }
        val prestation =  presentationApi.getPresentation(
            presentationId = prestationId?.let { UUID.fromString(it) }
        )
        return ResponseEntity.ok(prestation)
    }


    @GetMapping("/by-center-sport")
    fun getPrestationsByCenterSport(
        @RequestParam(required = true)  centerSportId: String,
    ): ResponseEntity<Any> {
        tryCatchUUID(centerSportId) ?: return ResponseEntity.badRequest().body("Invalid UUID for centerSportId")
        val prestation =  presentationApi.getPresentationsByCenterSport(
            centerSportId = UUID.fromString(centerSportId)
        )
        return ResponseEntity.ok(prestation)
    }


    @PostMapping("/update")
    fun updatePrestation(
        @RequestParam(required = true)  prestationId: String,
        @RequestParam(required = true)  newPrice: Double,
    ): ResponseEntity<Any> {
        tryCatchUUID(prestationId) ?: return ResponseEntity.badRequest().body("Invalid UUID for prestationId")
        return presentationApi.updatePresentation(
            presentationId = UUID.fromString(prestationId),
            newPrice = newPrice
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { prestation ->
                ResponseEntity.ok(prestation)
            }
        )
    }

}