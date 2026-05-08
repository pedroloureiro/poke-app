package com.loreal.pokeapp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

interface PokemonService {
    suspend fun getFirstPage(): Result<PaginatedResponse<APIResource.Named>>
    suspend fun getPage(url: String): Result<PaginatedResponse<APIResource.Named>>
}

class PokemonServiceImpl @Inject constructor(private val client: HttpClient) : PokemonService {
    override suspend fun getFirstPage(): Result<PaginatedResponse<APIResource.Named>> {
        //TODO: move base url to some common place
        return client
            .get("https://pokeapi.co/api/v2/pokemon")
            .toResult<PaginatedResponse<APIResource.Named>>()
    }

    override suspend fun getPage(url: String): Result<PaginatedResponse<APIResource.Named>> {
        return client
            .get(url)
            .toResult<PaginatedResponse<APIResource.Named>>()
    }
}