package com.loreal.pokeapp.data

import com.loreal.pokeapp.data.database.PokeDatabase
import com.loreal.pokeapp.data.network.APIResource
import com.loreal.pokeapp.data.network.PaginatedResponse
import com.loreal.pokeapp.data.network.PokemonService
import javax.inject.Inject

interface PokemonRepository {
    suspend fun getAll(): Result<PaginatedResponse<APIResource.Named>>
}

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokemonService,
    private val pokeDatabase: PokeDatabase
) : PokemonRepository {
    override suspend fun getAll(): Result<PaginatedResponse<APIResource.Named>> =
        pokemonService.getAll()
}