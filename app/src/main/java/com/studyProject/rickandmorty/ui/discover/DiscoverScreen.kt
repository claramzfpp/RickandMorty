package com.studyProject.rickandmorty.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.domain.model.Location
import com.studyProject.rickandmorty.ui.character.CharacterUiState
import com.studyProject.rickandmorty.ui.character.CharacterViewModel
import com.studyProject.rickandmorty.ui.character.SearchUiState
import com.studyProject.rickandmorty.ui.common.CharacterCell
import com.studyProject.rickandmorty.ui.common.CharacterCellSize
import com.studyProject.rickandmorty.ui.common.EpisodeCell
import com.studyProject.rickandmorty.ui.common.ErrorContent
import com.studyProject.rickandmorty.ui.common.LoadingContent
import com.studyProject.rickandmorty.ui.common.LocationCell
import com.studyProject.rickandmorty.ui.common.RMCarousel
import com.studyProject.rickandmorty.ui.location.LocationUiState
import com.studyProject.rickandmorty.ui.location.LocationViewModel
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
    onCharacterClick: (Int) -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    onSeeAllLocationsClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    val locationState by locationViewModel.state.collectAsStateWithLifecycle()

    DiscoverContent(
        state = state,
        searchQuery = searchQuery,
        searchState = searchState,
        locationState = locationState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onCharacterClick = onCharacterClick,
        onSeeAllClick = onSeeAllClick,
        onSeeAllLocationsClick = onSeeAllLocationsClick,
        modifier = modifier,
    )
}

// "sem estado" (stateless): só recebe os dados e o callback, e desenha
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiscoverContent(
    state: CharacterUiState,
    searchQuery: String,
    searchState: SearchUiState,
    locationState: LocationUiState,
    onSearchQueryChanged: (String) -> Unit,
    onCharacterClick: (Int) -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    onSeeAllLocationsClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val background = MaterialTheme.colorScheme.background
    var shouldShowSearchBar by rememberSaveable { mutableStateOf(false) } // by ... == @State

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold( // layout shell, uma especie de body com modifiers
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { DiscoverTitle() },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = background,
                        scrolledContainerColor = background,
                    ),
                    actions = {
                        IconButton(onClick = {
                            shouldShowSearchBar = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Localized description",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
            containerColor = background,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(background),
            ) {
                EpisodeCarousel(modifier = Modifier.fillMaxWidth())

                // switch (Swift)
                when (state) {
                    CharacterUiState.Loading -> LoadingContent(Modifier.weight(1f).fillMaxWidth())
                    is CharacterUiState.Loaded -> CharacterCarousel(
                        characters = state.characters,
                        onCharacterClick = onCharacterClick,
                        onSeeAllClick = onSeeAllClick,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    is CharacterUiState.Error -> ErrorContent(state.message, Modifier.weight(1f).fillMaxWidth())
                }

                when (locationState) {
                    LocationUiState.Loading -> LoadingContent(
                        Modifier.fillMaxWidth().height(160.dp)
                    )

                    is LocationUiState.Loaded -> LocationCarousel(
                        locations = locationState.locations,
                        onSeeAllClick = onSeeAllLocationsClick,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    is LocationUiState.Error -> ErrorContent(
                        locationState.message,
                        Modifier.fillMaxWidth().height(160.dp)
                    )
                }
            }
        }

        if (shouldShowSearchBar) {
            SearchScreen(
                searchQuery = searchQuery,
                searchState = searchState,
                onSearchQueryChange = onSearchQueryChanged,
                onClose = { shouldShowSearchBar = false },
                onCharacterClick = onCharacterClick,
            )
        }
    }
}

@Composable
private fun DiscoverTitle() {
    Text(
        text = "Discover",
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
private fun CharacterCarousel(
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
private fun LocationCarousel(
    locations: List<Location>,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RMCarousel(
        title = "Locations",
        onSeeAllClick = onSeeAllClick,
        modifier = modifier,
        shouldShowSeeAll = true,
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(locations.take(8), key = { it.id }) { location ->
                LocationCell(location.id, location.name, location.type, location.dimension)
            }
        }
    }
}

@Composable
private fun EpisodeCarousel(
    modifier: Modifier = Modifier,
) {
    val seasonNumber = (1..10).toList()
    val episodesNumber = arrayOf(10, 11, 8, 9)
    
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
                EpisodeCell(
                    season,
                    episodesNumber.random()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscoverContentPreview() {
    RickAndMortyTheme {
        DiscoverContent(
            state = CharacterUiState.Loaded(
                List(4) { index ->
                    Character(
                        id = index,
                        name = "Rick Sanchez",
                        status = "Alive",
                        species = "Human",
                        gender = "Male",
                        imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                        originName = "Earth (C-137)",
                    )
                }
            ),
            searchQuery = "",
            searchState = SearchUiState.Idle,
            locationState = LocationUiState.Loaded(
                listOf(
                    Location(id = 1, name = "Earth", type = "Planet", dimension = "Dimension C-137"),
                    Location(id = 2, name = "Citadel of Ricks", type = "Space station", dimension = "unknown"),
                    Location(id = 3, name = "Worldender's lair", type = "Planet", dimension = "unknown"),
                )
            ),
            onSearchQueryChanged = {},
            onSeeAllClick = {},
            onSeeAllLocationsClick = {},
        )
    }
}
