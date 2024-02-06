package org.architecture.sport.domain.utils

import io.konform.validation.ValidationBuilder
import java.time.Duration
import java.time.LocalDateTime

fun ValidationBuilder<LocalDateTime>.openHour(
    startHour: Int,
    endHour: Int
): io.konform.validation.Constraint<LocalDateTime> {
    return addConstraint("interval time open {startHour} to {endHour}", startHour.toString(), endHour.toString()) {
        it.isBefore(
            it.withHour(endHour).withMinute(0).withSecond(0)
        ) && it.isAfter(
            it.withHour(startHour).withMinute(0).withSecond(0)
        )
    }
}

fun ValidationBuilder<LocalDateTime>.notBeforeNow(): io.konform.validation.Constraint<LocalDateTime> {
    return addConstraint("not before now") {
        it.isAfter(LocalDateTime.now().minusDays(1))
    }
}


fun ValidationBuilder<LocalDateTime>.intervalTime(localDateTime: LocalDateTime): io.konform.validation.Constraint<LocalDateTime> {
    return addConstraint("interval time") {
        intervalBetweenTwoDate(it, localDateTime)
    }
}


fun intervalBetweenTwoDate(localDateTime: LocalDateTime, localDateTime1: LocalDateTime): Boolean {
    val interval = Duration.between(localDateTime, localDateTime1)
    return interval.abs().toHours() >= 1
}