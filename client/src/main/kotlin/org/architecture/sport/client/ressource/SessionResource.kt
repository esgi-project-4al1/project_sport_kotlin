package org.architecture.sport.client.ressource

import org.architecture.sport.client.dto.SessionDto
import org.architecture.sport.client.mapping.SessionMapper
import org.architecture.sport.client.utils.tryCatchUUID
import org.architecture.sport.domain.ports.client.SessionApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/session")
class SessionResource(
    private val sessionApi: SessionApi,
    private val sessionMapper: SessionMapper
) {


    @PostMapping
    fun createSession(
        @RequestBody(required = true)  sessionDto: SessionDto,
    ): ResponseEntity<Any> {
        if (sessionDto.material != null && tryCatchUUID(sessionDto.material) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID for material")
        }
        if (sessionDto.prestation != null && tryCatchUUID(sessionDto.prestation) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID for prestation")
        }
        if (tryCatchUUID(sessionDto.centerSportId) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID for centerSportId")
        }
        return sessionApi.createSession(
            sessionMapper.toDomain(sessionDto)
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { session ->
                ResponseEntity.ok(session)
            }
        )
    }

    @PostMapping(
        path = ["/join"],
    )
    fun joinSession(
        @RequestBody(required = true)  sessionId: String,
        @RequestBody(required = true)  userId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(sessionId) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID for sessionId")
        }
        if (tryCatchUUID(userId) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID for userId")
        }
        return sessionApi.joinSession(
            sessionId = UUID.fromString(sessionId),
            userId = UUID.fromString(userId)
        )
            .fold(
                { error ->
                    ResponseEntity.badRequest().body(error.message)
                },
                { session ->
                    ResponseEntity.ok(session)
                }
            )
    }

    @PostMapping(
        path = ["/unjoin"],
    )
    fun unJoinSession(
        @RequestBody(required = true)  sessionId: String,
        @RequestBody(required = true)  userId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(sessionId) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID for sessionId")
        }
        if (tryCatchUUID(userId) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID for userId")
        }
        return sessionApi.unJoinSession(
            sessionId = UUID.fromString(sessionId),
            userId = UUID.fromString(userId)
        )
            .fold(
                { error ->
                    ResponseEntity.badRequest().body(error.message)
                },
                { session ->
                    ResponseEntity.ok(session)
                }
            )
    }

    @PostMapping(
        path = ["/caution"],
    )
    fun getCaution(
        @RequestBody(required = true)  sessionId: String,
        @RequestBody(required = true)  userId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(sessionId) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID for sessionId")
        }
        if (tryCatchUUID(userId) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID for userId")
        }
        return sessionApi.returnCaution(
            sessionId = UUID.fromString(sessionId),
            userId = UUID.fromString(userId)
        )
            .fold(
                { error ->
                    ResponseEntity.badRequest().body(error.message)
                },
                { session ->
                    ResponseEntity.ok(session)
                }
            )
    }


    @GetMapping
    fun getSession(
        @RequestBody(required = false)  sessionId: String?,
    ): ResponseEntity<Any> {
        if (sessionId != null && tryCatchUUID(sessionId) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID")
        }
        val session = sessionApi.getSession(
            sessionId = sessionId?.let { UUID.fromString(it) }
        )
        return ResponseEntity.ok(session)
    }

    @GetMapping("/by-center-sport")
    fun getSessionsByCenterSport(
        @RequestBody(required = true)  centerSportId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(centerSportId) == null) {
            return ResponseEntity.badRequest().body("Invalid UUID")
        }
        val session = sessionApi.getSessionsByCenterSport(
            centerSportId = UUID.fromString(centerSportId)
        )
        return ResponseEntity.ok(session)
    }
}