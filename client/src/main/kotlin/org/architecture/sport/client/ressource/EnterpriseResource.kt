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
        @RequestBody(required = false) id: String?,
    ): ResponseEntity<Any> {
        if (id != null) {
            tryCatchUUID(id)?.let {
                return ResponseEntity.badRequest().body("Invalid UUID")
            }
        }
        val enterprise = enterpriseApi.gets(
            id = UUID.fromString(id)
        )
        return ResponseEntity.ok(enterprise)
    }


    @PostMapping("/add-users")
    fun addUsers(
        @RequestBody(required = true) enterpriseID: String,
        @RequestBody(required = true) userId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(enterpriseID) != null) return ResponseEntity.badRequest().body("Invalid UUID")
        if (tryCatchUUID(userId) != null) return ResponseEntity.badRequest().body("Invalid UUID")


        return enterpriseApi.addUsers(
            enterpriseID = UUID.fromString(enterpriseID),
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
        @RequestBody(required = true) enterpriseID: String,
        @RequestBody(required = true) userId: String,
    ): ResponseEntity<Any> {
        if (tryCatchUUID(enterpriseID) != null) return ResponseEntity.badRequest().body("Invalid UUID")
        if (tryCatchUUID(userId) != null) return ResponseEntity.badRequest().body("Invalid UUID")


        return enterpriseApi.removeUsers(
            id = UUID.fromString(enterpriseID),
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