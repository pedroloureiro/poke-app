package com.loreal.pokeapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.loreal.pokeapp.data.database.PokeDatabase
import com.loreal.pokeapp.data.database.pokemon.PokemonDao
import com.loreal.pokeapp.data.database.pokemon.PokemonEntity
import com.loreal.pokeapp.data.database.remote_key.RemoteKeyDao
import com.loreal.pokeapp.data.database.remote_key.RemoteKeyEntity
import com.loreal.pokeapp.data.network.PokemonService

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonService: PokemonService,
    private val pokeDatabase: PokeDatabase, //TODO: pass some sort of manager; direct access to database is no good
    private val pokemonDao: PokemonDao,
    private val remoteKeyDao: RemoteKeyDao
) : RemoteMediator<Int, PokemonEntity>() {
    companion object {
        private const val REMOTE_KEY_ID = "pokemon"
    }

    override suspend fun initialize(): InitializeAction {
        //TODO: might be relevant because This method runs
        // before any loading is performed, so you can manipulate
        // the database before triggering any local or remote
        // loads, The most common case is to set a validity period
        // for the cached data
        return super.initialize()
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val next = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // RETRIEVE NEXT OFFSET FROM DATABASE
                    val remoteKey = remoteKeyDao.getById(REMOTE_KEY_ID)
                    if (remoteKey == null || remoteKey.next == null) // END OF PAGINATION REACHED
                        return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey.next
                }
            }
            // MAKE API CALL
            val paginatedResponse = (next?.let { url ->
                pokemonService.getPage(url)
            } ?: pokemonService.getFirstPage()).getOrThrow()
            val results = paginatedResponse.results
            // SAVE RESULTS AND NEXT OFFSET TO DATABASE
            pokeDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // IF REFRESHING, CLEAR DATABASE FIRST
                    pokemonDao.clearAll()
                    remoteKeyDao.deleteById(REMOTE_KEY_ID)
                }
                pokemonDao.insertAll(
                    results.map { PokemonEntity(it.name, it.url) }
                )
                remoteKeyDao.insert(
                    RemoteKeyEntity(id = REMOTE_KEY_ID, next = paginatedResponse.next)
                )
            }
            // CHECK IF END OF PAGINATION REACHED
            MediatorResult.Success(endOfPaginationReached = paginatedResponse.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}