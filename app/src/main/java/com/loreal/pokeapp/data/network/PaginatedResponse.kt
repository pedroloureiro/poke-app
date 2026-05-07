package com.loreal.pokeapp.data.network

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<out T : APIResource>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)