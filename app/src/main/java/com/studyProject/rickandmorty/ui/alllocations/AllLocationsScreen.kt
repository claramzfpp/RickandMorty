package com.studyProject.rickandmorty.ui.alllocations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.studyProject.rickandmorty.domain.model.Location
import com.studyProject.rickandmorty.ui.common.ErrorContent
import com.studyProject.rickandmorty.ui.common.LoadingContent
import com.studyProject.rickandmorty.ui.common.LocationGrid as SharedLocationGrid
import com.studyProject.rickandmorty.ui.location.LocationUiState
import com.studyProject.rickandmorty.ui.location.LocationViewModel
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun AllLocationsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()

    AllLocationsContent(
        state = state,
        isLoadingMore = isLoadingMore,
        onLoadMore = viewModel::loadMore,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AllLocationsContent(
    state: LocationUiState,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Locations",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = background,
                    scrolledContainerColor = background,
                ),
            )
        },
        containerColor = background,
    ) { innerPadding ->
        val contentModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(background)

        when (state) {
            LocationUiState.Loading -> LoadingContent(contentModifier)
            is LocationUiState.Loaded -> AllLocationsGrid(
                locations = state.locations,
                isLoadingMore = isLoadingMore,
                onLoadMore = onLoadMore,
                modifier = contentModifier,
            )
            is LocationUiState.Error -> ErrorContent(state.message, contentModifier)
        }
    }
}

@Composable
private fun AllLocationsGrid(
    locations: List<Location>,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()

    val reachedEnd by remember {
        derivedStateOf {
            val lastVisible = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = gridState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - 4
        }
    }

    LaunchedEffect(reachedEnd) {
        if (reachedEnd) onLoadMore()
    }

    SharedLocationGrid(
        locations = locations,
        modifier = modifier,
        gridState = gridState,
        footer = {
            if (isLoadingMore) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun AllLocationsContentPreview() {
    RickAndMortyTheme {
        AllLocationsContent(
            state = LocationUiState.Loaded(
                listOf(
                    Location(id = 1, name = "Earth", type = "Planet", dimension = "Dimension C-137"),
                    Location(id = 2, name = "Citadel of Ricks", type = "Space station", dimension = "unknown"),
                    Location(id = 3, name = "Worldender's lair", type = "Planet", dimension = "unknown"),
                    Location(id = 4, name = "Anatomy Park", type = "Microverse", dimension = "unknown"),
                )
            ),
            isLoadingMore = false,
            onLoadMore = {},
            onBackClick = {},
        )
    }
}
