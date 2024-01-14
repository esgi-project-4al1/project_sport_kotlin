package org.architecture.sport.client.ressource

import org.architecture.sport.client.dto.UserCreateDto
import org.architecture.sport.client.dto.UserMoneyDto
import org.architecture.sport.client.mapping.UserMapper
import org.architecture.sport.domain.ports.client.UserApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class UserController(
    private val userMapper: UserMapper,
    private val userApi: UserApi
) {


    @PostMapping
    fun createUser(
        userCreateDto: UserCreateDto,
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


    @PostMapping
    fun updateMoneyUser(
        userMoneyDto: UserMoneyDto,
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

}