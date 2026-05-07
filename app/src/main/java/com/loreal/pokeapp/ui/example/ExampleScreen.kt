package com.loreal.pokeapp.ui.example

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.loreal.pokeapp.ui.theme.PokeAppTheme

@Composable
fun TaskScreen(modifier: Modifier = Modifier, viewModel: ExampleViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) { viewModel.fetchTasks() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TaskScreenContent(modifier, uiState)
}

@Composable
fun TaskScreenContent(modifier: Modifier = Modifier, uiState: ExampleUiState = ExampleUiState()) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(uiState.pokemonList) { pokemon ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                //AsyncImage(model = pokemon.url, contentDescription = null)
                Text(pokemon.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    PokeAppTheme {
        TaskScreenContent()
    }
}