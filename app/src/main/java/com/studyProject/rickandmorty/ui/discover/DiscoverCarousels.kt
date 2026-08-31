package com.studyProject.rickandmorty.ui.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.domain.model.Location
import com.studyProject.rickandmorty.ui.common.CharacterCell
import com.studyProject.rickandmorty.ui.common.CharacterCellSize
import com.studyProject.rickandmorty.ui.common.SeasonCell
import com.studyProject.rickandmorty.ui.common.LocationCell
import com.studyProject.rickandmorty.ui.common.RMCarousel

@Composable
fun CharacterCarousel(
    characters: List<Character>,
    onCharacterClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RMCarousel(
        title = "Characters",
        onSeeAllClick = onSeeAllClick,
        modifier = modifier,
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(characters.take(5), key = { it.id }) { character ->
                CharacterCell(
                    character,
                    size = CharacterCellSize.Discover,
                    onClick = { onCharacterClick(character.id) },
                )
            }
        }
    }
}

@Composable
fun LocationCarousel(
    locations: List<Location>,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLocationClick: (Int) -> Unit = {},
) {
    RMCarousel(
        title = "Locations",
        onSeeAllClick = onSeeAllClick,
        modifier = modifier,
        shouldShowSeeAll = true,
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.height(204.dp),
        ) {
            items(locations.take(8), key = { it.id }) { location ->
                LocationCell(
                    location.id,
                    location.name,
                    location.type,
                    location.dimension,
                    onClick = { onLocationClick(location.id) },
                )
            }
        }
    }
}

@Composable
fun EpisodeCarousel(
    modifier: Modifier = Modifier,
) {
    val seasonNumber = (1..9).toList()

    RMCarousel(
        title = "Episodes",
        modifier = modifier,
        shouldShowSeeAll = false
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(seasonNumber, key = { it }) { season ->
                SeasonCell(
                    season,
                    if (season == 1) 11 else 10
                )
            }
        }
    }
}
