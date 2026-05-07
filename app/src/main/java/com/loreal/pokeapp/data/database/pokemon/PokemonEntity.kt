package com.loreal.pokeapp.data.database.pokemon

import androidx.room.Entity

@Entity(tableName = "pokemon", primaryKeys = ["name", "url"])
data class PokemonEntity(
    val name: String,
    val url: String
)