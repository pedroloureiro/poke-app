package com.loreal.pokeapp.ui.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loreal.pokeapp.domain.ExampleUseCase
import com.loreal.pokeapp.domain.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    fun fetchTasks() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val pokemonList = useCase.getAllPokemon().getOrNull() ?: emptyList()
            //TODO: handle error
            _uiState.update { it.copy(loading = false, pokemonList = pokemonList) }
        }
    }
}