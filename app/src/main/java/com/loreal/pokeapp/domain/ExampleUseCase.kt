package com.loreal.pokeapp.domain

import androidx.paging.PagingData
import com.loreal.pokeapp.data.PokemonRepository
import com.loreal.pokeapp.data.database.pokemon.PokemonEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ExampleUseCase {
    fun getPagedPokemon(): Flow<PagingData<PokemonEntity>>
}

class ExampleUseCaseImpl @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ExampleUseCase {
    override fun getPagedPokemon(): Flow<PagingData<PokemonEntity>> {
        return pokemonRepository.getPagedPokemon()
    }
}