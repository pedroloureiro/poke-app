package com.loreal.pokeapp.di

import com.loreal.pokeapp.data.ExampleRepository
import com.loreal.pokeapp.domain.ExampleUseCase
import com.loreal.pokeapp.domain.ExampleUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun providesExampleUseCase(exampleRepository: ExampleRepository): ExampleUseCase {
        return ExampleUseCaseImpl(exampleRepository)
    }
}