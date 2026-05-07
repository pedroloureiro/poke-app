package com.loreal.pokeapp.di

import android.content.Context
import com.loreal.pokeapp.data.PokemonRepository
import com.loreal.pokeapp.data.PokemonRepositoryImpl
import com.loreal.pokeapp.data.database.PokeDatabase
import com.loreal.pokeapp.data.network.PokemonService
import com.loreal.pokeapp.data.network.PokemonServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
            }
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun providePokeDatabase(@ApplicationContext context: Context): PokeDatabase {
        return PokeDatabase.build(context)
    }

    @Provides
    @Singleton
    fun providePokemonService(client: HttpClient): PokemonService {
        return PokemonServiceImpl(client)
    }

    @Provides
    @Singleton
    fun providesPokemonRepository(
        pokemonService: PokemonService,
        pokeDatabase: PokeDatabase
    ): PokemonRepository {
        return PokemonRepositoryImpl(pokemonService, pokeDatabase)
    }
}