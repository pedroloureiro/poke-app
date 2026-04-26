package com.loreal.pokeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loreal.pokeapp.ui.components.ListItem
import com.loreal.pokeapp.ui.theme.PokeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeAppTheme {
                MyApp()
            }
        }
    }
}

//TODO: this needs to come from Repo
private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { ExerciseModel(it.first, it.second) }

//TODO: this needs to come from Repo
private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { ExerciseModel(it.first, it.second) }

//TODO: repackage as UI Model
data class ExerciseModel(
    @field:DrawableRes val imageRes: Int,
    @field:StringRes val labelRes: Int
)

@Composable
fun MyApp() {
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

@Composable
fun Content(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier.padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppSearchBar()
        AlignYourBodySection(alignYourBodyData)
        FavoriteCollectionsSection(favoriteCollectionsData)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar() {
    val textFieldState: TextFieldState = TextFieldState()
    SearchBar(
        modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 16.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = textFieldState.text.toString(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                onSearch = { },
                expanded = false,
                onExpandedChange = { },
                placeholder = { Text("Search") }
            )
        },
        expanded = false,
        onExpandedChange = { },
        windowInsets = WindowInsets(0)
    ) {}
}

@Composable
fun AlignYourBodySection(data: List<ExerciseModel>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Align your body", modifier = Modifier.padding(horizontal = 16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data) { model ->
                model.ToListItem(type = ListItem.Type.HORIZONTAL_ROW)
            }
        }
    }
}

@Composable
fun FavoriteCollectionsSection(data: List<ExerciseModel>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Favorite collections", modifier = Modifier.padding(horizontal = 16.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.height(168.dp)
        ) {
            items(data) { model ->
                model.ToListItem(type = ListItem.Type.HORIZONTAL_GRID)
            }
        }
    }
}

@Composable
fun ExerciseModel.ToListItem(type: ListItem.Type) = ListItem(
    config = ListItem.Config(
        type = type,
        imageRes = imageRes,
        labelRes = labelRes
    )
)

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

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    PokeAppTheme {
        MyApp()
    }
}