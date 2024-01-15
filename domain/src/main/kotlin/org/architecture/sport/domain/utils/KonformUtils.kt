package org.architecture.sport.domain.utils

import io.konform.validation.ValidationBuilder
import java.time.Duration
import java.time.LocalDateTime



fun ValidationBuilder<LocalDateTime>.openHourStart(hour: Int): io.konform.validation.Constraint<LocalDateTime> {
    return addConstraint("openHour start '{0}'", hour.toString()) {
        it.isAfter(
            it.withHour(hour).withMinute(0).withSecond(0)
        )
    }
}


fun ValidationBuilder<LocalDateTime>.openHourEnd(hour: Int): io.konform.validation.Constraint<LocalDateTime> {
    return addConstraint("openHour end '{0}'", hour.toString()) {
        it.isAfter(
            it.withHour(hour).withMinute(0).withSecond(0)
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
    return  interval.abs().toHours() >= 1
}