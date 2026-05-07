package com.loreal.pokeapp.domain

import com.loreal.pokeapp.data.PokemonRepository
import javax.inject.Inject

interface ExampleUseCase {
    suspend fun getAllPokemon(): Result<List<Pokemon>>
}

class ExampleUseCaseImpl @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ExampleUseCase {
    override suspend fun getAllPokemon(): Result<List<Pokemon>> {
        val result = pokemonRepository.getAll()
        //TODO: make quick use extension
        result.getOrNull()?.let { namedPaginatedResponse ->
            val pokemonList = namedPaginatedResponse.results.map { Pokemon(it.name, it.url) }
            return Result.success(pokemonList)
        }

        return Result.failure(result.exceptionOrNull() ?: Exception("Something went wrong"))
    }
}