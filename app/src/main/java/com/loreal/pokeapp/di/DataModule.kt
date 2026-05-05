package com.loreal.pokeapp.di

import com.loreal.pokeapp.data.ExampleRepository
import com.loreal.pokeapp.data.ExampleRepositoryImpl
import com.loreal.pokeapp.network.ExampleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesExampleRepository(exampleService: ExampleService): ExampleRepository {
        return ExampleRepositoryImpl(exampleService)
    }
}