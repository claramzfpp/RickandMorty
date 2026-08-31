package com.studyProject.rickandmorty.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyProject.rickandmorty.domain.model.Location

@Composable
fun LocationGrid(
    locations: List<Location>,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    footer: LazyGridScope.() -> Unit = {},
) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(locations, key = { it.id }) { location ->
            LocationCell(location.id, location.name, location.type, location.dimension)
        }

        footer()
    }
}
