package org.architecture.sport.client.ressource

import org.architecture.sport.client.dto.PrestationDto
import org.architecture.sport.client.mapping.PrestationMapper
import org.architecture.sport.client.utils.tryCatchUUID
import org.architecture.sport.domain.ports.client.PresentationApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RequestMapping("/prestation")
@RestController
class PrestationResource(
    private val prestationMapper: PrestationMapper,
    private val prestationApi: PresentationApi,
) {

    @PostMapping
    fun createPrestation(
        prestationDto: PrestationDto,
        centerSportId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(centerSportId) != null) return ResponseEntity.badRequest().body("Invalid UUID for centerSportId")
        return prestationApi.createPresentation(
            presentation = prestationMapper.toDomain(prestationDto),
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
        prestationId: String?,
    ): ResponseEntity<Any> {
        if (prestationId != null){
            tryCatchUUID(prestationId)?.let {
                return ResponseEntity.badRequest().body("Invalid UUID")
            }
        }
        val prestation =  prestationApi.getPresentation(
            presentationId = UUID.fromString(prestationId)
        )
        return ResponseEntity.ok(prestation)
    }


    @GetMapping("/by-center-sport")
    fun getPrestationsByCenterSport(
        centerSportId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(centerSportId) != null) return ResponseEntity.badRequest().body("Invalid UUID for centerSportId")
        val prestation =  prestationApi.getPresentationsByCenterSport(
            centerSportId = UUID.fromString(centerSportId)
        )
        return ResponseEntity.ok(prestation)
    }


    @PostMapping("/update")
    fun updatePrestation(
        prestationId: String,
        newPrice: Double,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(prestationId) != null) return ResponseEntity.badRequest().body("Invalid UUID for prestationId")
        return prestationApi.updatePresentation(
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