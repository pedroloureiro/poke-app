package com.loreal.pokeapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.loreal.pokeapp.data.database.PokeDatabase
import com.loreal.pokeapp.data.database.pokemon.PokemonEntity
import com.loreal.pokeapp.data.database.remote_key.RemoteKeyEntity
import com.loreal.pokeapp.data.network.PokemonService
import com.loreal.pokeapp.domain.Pokemon

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonService: PokemonService,
    private val pokeDatabase: PokeDatabase
) : RemoteMediator<Int, Pokemon>() {
    private val REMOTE_KEY_ID = "pokemon"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        return try {
            val next = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // RETRIEVE NEXT OFFSET FROM DATABASE
                    val remoteKey = pokeDatabase.remoteKeyDao.getById(REMOTE_KEY_ID)
                    if (remoteKey == null || remoteKey.next == null) // END OF PAGINATION REACHED
                        return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey.next
                }
            }
            // MAKE API CALL
            val result = pokemonService.getAll(
                /*offset = offset,
                limit = state.config.pageSize,*/
            )
            val paginatedResponse = result.getOrNull()
            val results = paginatedResponse?.results ?: emptyList()
            // SAVE RESULTS AND NEXT OFFSET TO DATABASE
            pokeDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // IF REFRESHING, CLEAR DATABASE FIRST
                    pokeDatabase.pokemonDao.clearAll()
                    pokeDatabase.remoteKeyDao.deleteById(REMOTE_KEY_ID)
                }
                pokeDatabase.pokemonDao.insertAll(
                    results.map { PokemonEntity(it.name, it.url) }
                )
                pokeDatabase.remoteKeyDao.insert(
                    RemoteKeyEntity(id = REMOTE_KEY_ID, next = next)
                )
            }
            // CHECK IF END OF PAGINATION REACHED
            MediatorResult.Success(endOfPaginationReached = results.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}