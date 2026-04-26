package com.loreal.pokeapp.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

sealed interface ListItem {
    data class Config(
        val type: Type,
        @field:DrawableRes val imageRes: Int,
        @field:StringRes val labelRes: Int
    )

    enum class Type { HORIZONTAL_ROW, HORIZONTAL_GRID }
}

@Composable
fun ListItem(config: ListItem.Config) {
    when (config.type) {
        ListItem.Type.HORIZONTAL_ROW -> HorizontalRowListItem(
            imageRes = config.imageRes,
            label = stringResource(config.labelRes)
        )

        ListItem.Type.HORIZONTAL_GRID -> HorizontalGridListItem(
            imageRes = config.imageRes,
            label = stringResource(config.labelRes)
        )
    }
}

@Composable
fun HorizontalRowListItem(@DrawableRes imageRes: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(shape = CircleShape) {
            Image(
                painterResource(imageRes),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(102.dp)
            )
        }
        Text(label)
    }
}

@Composable
fun HorizontalGridListItem(@DrawableRes imageRes: Int, label: String) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.width(255.dp)
        ) {
            Image(
                painterResource(imageRes),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(label)
        }
    }
}