package org.architecture.sport.client.ressource

import org.architecture.sport.client.utils.tryCatchUUID
import org.architecture.sport.domain.model.Enterprise
import org.architecture.sport.domain.ports.client.EnterpriseApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/enterprise")
class EnterpriseResource(
    private val enterpriseApi: EnterpriseApi,
) {


    @PostMapping
    fun create(
        @RequestBody(required = true) name: String,
    ): ResponseEntity<Any> {
        return enterpriseApi.create(
            Enterprise(
                name = name
            )
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { enterprise ->
                ResponseEntity.ok(enterprise)
            }
        )
    }

    @GetMapping
    fun get(
        @RequestParam(required = false) id: String?,
    ): ResponseEntity<Any> {
        if (id != null) {
            tryCatchUUID(id) ?: return ResponseEntity.badRequest().body("Invalid UUID")
        }
        val enterprise = enterpriseApi.gets(
            id = id?.let { UUID.fromString(it) }
        )
        return ResponseEntity.ok(enterprise)
    }


    @PostMapping("/add-users")
    fun addUsers(
        @RequestParam(required = true) enterpriseId: String,
        @RequestParam(required = true) userId: String,
    ): ResponseEntity<Any> {
        tryCatchUUID(enterpriseId) ?: return ResponseEntity.badRequest().body("Invalid UUID enterprise")
        tryCatchUUID(userId) ?: return ResponseEntity.badRequest().body("Invalid UUID")
        return enterpriseApi.addUsers(
            enterpriseID = UUID.fromString(enterpriseId),
            userId = UUID.fromString(userId)
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { enterprise ->
                ResponseEntity.ok(enterprise)
            }
        )
    }


    @PostMapping("/remove-users")
    fun removeUsers(
        @RequestParam(required = true) enterpriseId: String,
        @RequestParam(required = true) userId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(enterpriseId) == null) return ResponseEntity.badRequest().body("Invalid UUID enterprise")
        if (tryCatchUUID(userId) == null) return ResponseEntity.badRequest().body("Invalid UUID user")


        return enterpriseApi.removeUsers(
            id = UUID.fromString(enterpriseId),
            userId = UUID.fromString(userId)
        ).fold(
            { error ->
                ResponseEntity.badRequest().body(error.message)
            },
            { enterprise ->
                ResponseEntity.ok(enterprise)
            }
        )
    }


}