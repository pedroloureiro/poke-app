package com.loreal.pokeapp.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ExampleResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<JsonElement>
)