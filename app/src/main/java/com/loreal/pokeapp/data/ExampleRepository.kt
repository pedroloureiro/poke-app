package com.loreal.pokeapp.data

import com.loreal.pokeapp.network.ExampleService
import javax.inject.Inject

interface ExampleRepository {
    suspend fun get(): ExampleResponse
}

class ExampleRepositoryImpl @Inject constructor(private val exampleService: ExampleService) :
    ExampleRepository {
    override suspend fun get(): ExampleResponse = exampleService.get()
}