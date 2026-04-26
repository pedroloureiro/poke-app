package com.loreal.pokeapp.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun ListItem(config: ListItem.Config, modifier: Modifier = Modifier) {
    when (config.type) {
        ListItem.Type.HORIZONTAL_ROW -> HorizontalRowListItem(
            imageRes = config.imageRes,
            label = stringResource(config.labelRes),
            modifier = modifier
        )

        ListItem.Type.HORIZONTAL_GRID -> HorizontalGridListItem(
            imageRes = config.imageRes,
            label = stringResource(config.labelRes),
            modifier = modifier
        )
    }
}

@Composable
fun HorizontalRowListItem(
    @DrawableRes imageRes: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Image(
            painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = label,
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun HorizontalGridListItem(
    @DrawableRes imageRes: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(255.dp)
        ) {
            Image(
                painterResource(imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = label,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}