package org.architecture.sport.client.ressource

import org.architecture.sport.client.dto.RoomDto
import org.architecture.sport.client.mapping.RoomMapper
import org.architecture.sport.client.utils.tryCatchUUID
import org.architecture.sport.domain.ports.client.RoomApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/room")
class RoomResource(
    private val roomApi: RoomApi,
    private val roomMapper: RoomMapper
) {

    @PostMapping
    fun createRoom(
        @RequestBody(required = true)  roomDto: RoomDto
    ): ResponseEntity<Any> {
        roomMapper.toDomain(roomDto).let {
            return roomApi.createRoom(
                centerSportId = it.centerSportId,
                room = it
            ).fold(
                { error ->
                    ResponseEntity.badRequest().body(error.message)
                },
                { room ->
                    ResponseEntity.ok(room)
                }
            )
        }
    }

    @GetMapping
    fun getRoom(
        @RequestParam(required = false)  roomId: String?
    ): ResponseEntity<Any> {
        if (roomId != null && tryCatchUUID(roomId) == null) {
            ResponseEntity.badRequest().body("Invalid UUID")
        }
        val room = roomApi.getRoom(
            id = roomId?.let { UUID.fromString(it) }
        )
        return ResponseEntity.ok(room)
    }

    @GetMapping("/by-center-sport")
    fun getRoomByCenterSport(
        @RequestParam(required = true)  centerSportId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(centerSportId) == null) {
            ResponseEntity.badRequest().body("Invalid UUID")
        }
        val room = roomApi.getRoomByCenterSport(
            centerSportId = UUID.fromString(centerSportId)
        )
        return ResponseEntity.ok(room)
    }

    @PostMapping("/update-price")
    fun updatePrice(
        @RequestParam(required = true)  roomId: String,
        @RequestParam(required = true)  price: Double
    ): ResponseEntity<Any> {
        if (tryCatchUUID(roomId) == null) {
            ResponseEntity.badRequest().body("Invalid UUID")
        }
        return roomApi.updatePrice(
            id = UUID.fromString(roomId),
            price = price
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { room ->
                ResponseEntity.ok(room)
            }
        )
    }
}