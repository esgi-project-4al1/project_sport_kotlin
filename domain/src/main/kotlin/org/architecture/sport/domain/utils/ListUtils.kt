package org.architecture.sport.domain.utils

fun <T>  T?.toList(): List<T> {
    return if (this == null) {
        emptyList()
    } else {
        listOf(this)
    }
}