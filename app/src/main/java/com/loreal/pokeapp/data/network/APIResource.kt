package com.loreal.pokeapp.data.network

import kotlinx.serialization.Serializable

sealed interface APIResource {
    @Serializable
    data class Named(val name: String, val url: String) : APIResource

    @Serializable
    data class Unnamed(val url: String) : APIResource
}