package org.architecture.sport.client.mapping

import org.architecture.sport.client.dto.UserCreateDto
import org.architecture.sport.client.dto.UserMoneyDto
import org.architecture.sport.domain.model.Address
import org.architecture.sport.domain.model.User
import org.architecture.sport.domain.model.UserMoney
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserMapper {

    fun toUserCreateDTOtoUser(
        userCreateDto: UserCreateDto,
    ): User {
        val address = Address(
            street = userCreateDto.street,
            city = userCreateDto.city,
            postalCode = userCreateDto.postalCode,
            country = userCreateDto.country
        )
        return User(
            id = UUID.randomUUID(),
            firstName = userCreateDto.firstName,
            lastName = userCreateDto.lastName,
            email = userCreateDto.email,
            phoneNumber = userCreateDto.phoneNumber,
            address = address,
            money = userCreateDto.money
        )
    }


    fun toUserToUserCreateDTO(
        user: User,
    ): UserCreateDto {
        return UserCreateDto(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phoneNumber = user.phoneNumber,
            street = user.address.street,
            city = user.address.city,
            postalCode = user.address.postalCode,
            country = user.address.country,
            money = user.money
        )
    }


    fun toClient(
        user: UserMoney,
    ): UserMoneyDto {
        return UserMoneyDto(
            id = user.id.toString(),
            money = user.money
        )
    }

    fun toDomain(
        user: UserMoneyDto,
    ): UserMoney {
        return UserMoney(
            id = UUID.fromString(user.id),
            money = user.money
        )
    }
}