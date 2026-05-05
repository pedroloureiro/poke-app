package com.loreal.pokeapp.network

import com.loreal.pokeapp.data.ExampleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

interface ExampleService {
    suspend fun get(): ExampleResponse
}

class ExampleServiceImpl @Inject constructor(private val client: HttpClient) : ExampleService {
    override suspend fun get(): ExampleResponse {
        return client.get("https://pokeapi.co/api/v2/pokemon").body()
    }
}