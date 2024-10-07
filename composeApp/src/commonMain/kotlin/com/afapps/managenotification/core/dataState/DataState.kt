package com.afapps.managenotification.core.dataState


sealed class DataState<out T : Any> {
    data class Success<out T : Any>(val data: T?, val message: String? = null,) : DataState<T>()
    data class Error(val failure: Failure) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
}

sealed class Failure {
    data class NetworkConnection(val message: String? = null) : Failure()
    data class ServerError(val statusCode: Int, val message: String? = null, val errors: String? = null) : Failure()
    data class ThrowableFailure(val message: String? = null) : Failure()
}

fun Failure.validationError(): String {
    return when (this) {
        is Failure.NetworkConnection -> this.message ?: "Network connection error"
        is Failure.ServerError -> this.errors ?: this.message ?: "Server error"
        is Failure.ThrowableFailure -> this.message ?: "Unknown error"
    }
}