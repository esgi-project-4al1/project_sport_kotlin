package org.architecture.sport.domain.generate

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import org.architecture.sport.domain.error.ApplicationError
import org.architecture.sport.domain.model.*
import java.time.LocalDateTime
import java.util.*

val config = fakerConfig {
    random = Random(1)
    locale = "fr"
}
val faker = Faker(config)
fun generateUser(): User {
    return User(
        id = UUID.randomUUID(),
        firstName = faker.name.firstName(),
        lastName = faker.name.lastName(),
        email = faker.internet.email(),
        address = generateAddress(),
        money = 0.0,
        phoneNumber = faker.phoneNumber.phoneNumber(),
    )
}

fun generateAddress(): Address {
    val faker = Faker()
    return Address(
        street = faker.address.streetAddress(),
        city = faker.address.city(),
        postalCode = faker.address.postcode(),
        country = faker.address.country(),
    )
}

fun generateApplicationError(
    context: String = "User",
    message: String = "User is not valid",
    value: Any = generateUser()
): ApplicationError {
    return ApplicationError(
        context = context,
        message = message,
        value = value
    )
}


fun generateUserMoney(
    id: UUID = UUID.randomUUID(),
    money: Double = 0.0
): UserMoney {
    return UserMoney(
        id = id,
        money = money
    )
}


fun generateCenterSport(): CenterSport {
    return CenterSport(
        id = UUID.randomUUID(),
        name = faker.name.name(),
        address = generateAddress(),
    )
}

fun generateMaterial(
    centerSportId: UUID = UUID.randomUUID(),
    listMaintenanceTime: List<LocalDateTime> = listOf()
): Material {
    return Material(
        id = UUID.randomUUID(),
        name = faker.name.name(),
        centerSportId = centerSportId,
        listMaintenanceTime = listMaintenanceTime,
        caution = 10.0,
    )
}


fun generateSession(
    price: Double = 40.0,
    startDate: LocalDateTime = LocalDateTime.of(2030, 12, 1, 9, 1),
    endDate: LocalDateTime = LocalDateTime.of(2030, 12, 1, 10, 1),
) = Session(
    id = UUID.randomUUID(),
    name = faker.name.name(),
    centerSportId = UUID.randomUUID(),
    price = price,
    description = faker.animal.name(),
    prestation = UUID.randomUUID(),
    material = UUID.randomUUID(),
    startDate = startDate,
    endDate = endDate,
    maxParticipants = 10,
    minParticipants = 1,
    history = emptyList(),
    participants = emptyList()
)

fun generatePrestation(): Prestation {
    return Prestation(
        id = UUID.randomUUID(),
        name = faker.commerce.productName(),
        price = 0.0,
        description = faker.dragonBall.races(),
        centerSportId = UUID.randomUUID(),
    )
}

fun generateHistory(
    date: LocalDateTime = LocalDateTime.now(),
    action: String = "Hello"
): History {
    return History(
        id = UUID.randomUUID(),
        date = date,
        action = action,
    )
}