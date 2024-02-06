package org.architecture.sport.client.ressource

import org.architecture.sport.client.dto.UserCreateDto
import org.architecture.sport.client.dto.UserMoneyDto
import org.architecture.sport.client.mapping.UserMapper
import org.architecture.sport.client.utils.tryCatchUUID
import org.architecture.sport.domain.ports.client.UserApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/user")
class UserController(
    private val userMapper: UserMapper,
    private val userApi: UserApi
) {


    @PostMapping
    fun createUser(
        @RequestBody(required = true) userCreateDto: UserCreateDto,
    ): ResponseEntity<Any> {
        return userApi.create(
            user = userMapper.toUserCreateDTOtoUser(userCreateDto)
        )
            .fold(
                { error ->
                    ResponseEntity.badRequest().body(error.message)
                },
                { user ->
                    ResponseEntity.ok(userMapper.toUserToUserCreateDTO(user))
                }
            )
    }


    @PostMapping("/update-money")
    fun updateMoneyUser(
        @RequestBody userMoneyDto: UserMoneyDto,
    ): ResponseEntity<Any> {
        return userApi.updateMoney(
            userMoney = userMapper.toDomain(userMoneyDto)
        )
            .fold(
                { error ->
                    ResponseEntity.badRequest().body(error.message)
                },
                { user ->
                    ResponseEntity.ok(user)
                }
            )
    }

    @GetMapping
    fun getUser(
        @RequestParam(required = false) userId: String?,
    ): ResponseEntity<Any> {
        if (userId != null){
            tryCatchUUID(userId) ?: return ResponseEntity.badRequest().body("Invalid UUID")
        }
        val user =  userApi.getUsers(
            id = userId?.let { UUID.fromString(userId)}
        )
        return ResponseEntity.ok(user.getOrNull())
    }

}