package org.architecture.sport.domain.error

data class ApplicationError(
    val context: String,
    val message: String,
    val cause: Throwable? = null,
    val value: Any? = null
)
