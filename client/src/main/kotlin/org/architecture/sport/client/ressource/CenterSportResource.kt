package org.architecture.sport.client.ressource

import org.architecture.sport.client.dto.CenterSportDto
import org.architecture.sport.client.mapping.CenterSportMapper
import org.architecture.sport.client.utils.tryCatchUUID
import org.architecture.sport.domain.ports.client.CenterSportApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/center-sport")
@RestController
class CenterSportResource(
    private val centerSportApi: CenterSportApi,
    private val centerSportMapper: CenterSportMapper
) {

    @PostMapping
    fun createCenterSport(
        @RequestBody(required = true) centerSportDto: CenterSportDto,
    ): ResponseEntity<Any> {
        return centerSportApi.create(
            centerSport = centerSportMapper.toDomain(centerSportDto)
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { centerSport ->
                ResponseEntity.ok(centerSport)
            }
        )
    }

    @GetMapping
    fun getCenterSport(
        @RequestBody(required = false) centerSportId: String?,
    ): ResponseEntity<Any> {
        if (centerSportId != null){
            tryCatchUUID(centerSportId)?.let {
                return ResponseEntity.badRequest().body("Invalid UUID")
            }
        }
        val centerSport =  centerSportApi.gets(
            id = UUID.fromString(centerSportId)
        )
        return ResponseEntity.ok(centerSport)
    }
}