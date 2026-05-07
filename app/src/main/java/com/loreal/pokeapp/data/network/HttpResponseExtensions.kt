package com.loreal.pokeapp.data.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T : Any> HttpResponse.toResult(): Result<T> {
    return when (status.value) {
        200 -> Result.success(body())
        400 -> Result.failure(NetworkException("Check your credentials and try again!"))
        401 -> Result.failure(NetworkException("Authorization Failed! Try Logging In again."))
        500, 503 -> Result.failure(NetworkException("Server Disruption! We are on fixing it."))
        504 -> Result.failure(NetworkException("Too much load at this time, try again later!"))
        else -> Result.failure(NetworkException("Something went wrong! Please try again or contact support."))
    }
}