package com.loreal.pokeapp.domain

import com.loreal.pokeapp.data.ExampleRepository
import javax.inject.Inject

interface ExampleUseCase {
    suspend fun getCountLabel(): String
}

class ExampleUseCaseImpl @Inject constructor(private val exampleRepository: ExampleRepository) :
    ExampleUseCase {
    override suspend fun getCountLabel(): String {
        return exampleRepository.get().count.toString()
    }
}