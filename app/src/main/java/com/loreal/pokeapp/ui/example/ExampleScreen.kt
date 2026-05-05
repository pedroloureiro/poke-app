package com.loreal.pokeapp.ui.example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loreal.pokeapp.ui.theme.PokeAppTheme

@Composable
fun TaskScreen(modifier: Modifier = Modifier, viewModel: ExampleViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) { viewModel.fetchTasks() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TaskScreenContent(modifier, uiState)
}

@Composable
fun TaskScreenContent(modifier: Modifier = Modifier, uiState: ExampleUiState = ExampleUiState()) {
    Box(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(text = uiState.countLabel ?: "0")
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    PokeAppTheme {
        TaskScreenContent()
    }
}