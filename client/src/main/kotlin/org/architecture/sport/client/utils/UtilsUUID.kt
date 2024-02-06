package org.architecture.sport.client.utils

import java.util.*


fun tryCatchUUID(
    uuid: String,
): UUID? {
    return try {
        UUID.fromString(uuid)
    } catch (e: Exception) {
        null
    }
}