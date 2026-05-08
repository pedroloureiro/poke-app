package com.loreal.pokeapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.loreal.pokeapp.data.database.pokemon.PokemonDao
import com.loreal.pokeapp.data.database.pokemon.PokemonEntity
import com.loreal.pokeapp.data.network.PokemonService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PokemonRepository {
    fun getPagedPokemon(): Flow<PagingData<PokemonEntity>>
}

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokemonService,
    private val pokemonRemoteMediator: PokemonRemoteMediator,
    private val pokemonDao: PokemonDao
) : PokemonRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedPokemon(): Flow<PagingData<PokemonEntity>> {
        return Pager(
            initialKey = null,
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            pagingSourceFactory = pokemonDao::pagingSource,
            remoteMediator = pokemonRemoteMediator
        ).flow
    }

}