package com.loreal.pokeapp.ui.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.loreal.pokeapp.data.database.pokemon.PokemonEntity
import com.loreal.pokeapp.domain.ExampleUseCase
import com.loreal.pokeapp.domain.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ExampleUiState(
    val pokemonList: List<Pokemon> = emptyList(),
    val loading: Boolean = false
)

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val useCase: ExampleUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExampleUiState())
    val uiState: StateFlow<ExampleUiState> = _uiState.asStateFlow()

    val pagedPokemon: Flow<PagingData<PokemonEntity>> =
        useCase.getPagedPokemon().cachedIn(viewModelScope)
}