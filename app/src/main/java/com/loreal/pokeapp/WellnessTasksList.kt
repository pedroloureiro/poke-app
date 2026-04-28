package com.loreal.pokeapp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

data class WellnessTask(
    val id: Int,
    val label: String,
    val initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}

@Composable
fun WellnessTasksList(
    list: List<WellnessTask>,
    onCheckedChanged: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked -> onCheckedChanged(task, checked) },
                onClose = { onCloseTask(task) })
        }
    }
}