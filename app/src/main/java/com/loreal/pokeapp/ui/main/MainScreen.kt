package com.loreal.pokeapp.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.loreal.pokeapp.ui.example.TaskScreen

@Composable
fun MainScreen() {
    Scaffold(
        topBar = ::AppBar,
        content = ::Content,
        bottomBar = ::BottomBar,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(title = { Text("Top app bar") })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(paddingValues: PaddingValues) {
    TaskScreen(modifier = Modifier.padding(paddingValues))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = {}
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile"
                )
            },
            label = { Text("Profile") },
            selected = false,
            onClick = {}
        )
    }
}