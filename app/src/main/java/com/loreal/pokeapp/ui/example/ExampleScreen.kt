package com.loreal.pokeapp.ui.example

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.loreal.pokeapp.data.database.pokemon.PokemonEntity

@Composable
fun TaskScreen(modifier: Modifier = Modifier, viewModel: ExampleViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.pagedPokemon.collectAsLazyPagingItems()
    TaskScreenContent(modifier, uiState, lazyPagingItems)
}

@Composable
fun TaskScreenContent(
    modifier: Modifier = Modifier,
    uiState: ExampleUiState = ExampleUiState(),
    lazyPagingItems: LazyPagingItems<PokemonEntity>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.name }
        ) { index ->
            lazyPagingItems[index]?.let { pokemon ->
                Text(pokemon.name)
            }
        }
    }
}