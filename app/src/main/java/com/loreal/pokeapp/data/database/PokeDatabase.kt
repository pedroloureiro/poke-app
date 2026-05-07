package com.loreal.pokeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.loreal.pokeapp.data.database.pokemon.PokemonDao
import com.loreal.pokeapp.data.database.pokemon.PokemonEntity
import com.loreal.pokeapp.data.database.remote_key.RemoteKeyDao
import com.loreal.pokeapp.data.database.remote_key.RemoteKeyEntity

private const val VERSION: Int = 1
private const val POKE_DB: String = "poke.db"

@Database(entities = [PokemonEntity::class, RemoteKeyEntity::class], version = VERSION)
abstract class PokeDatabase : RoomDatabase() {
    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            PokeDatabase::class.java,
            POKE_DB,
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }

    abstract val pokemonDao: PokemonDao
    abstract val remoteKeyDao: RemoteKeyDao
}