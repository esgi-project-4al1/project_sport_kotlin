package org.architecture.sport.client.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.UUID

class UtilsUUIDKtTest {

    @Test
    fun tryCatchUUID() {
        val test = UUID.randomUUID().toString()
        val hello = tryCatchUUID(test)
        println("hello  ${hello}")
    }
}