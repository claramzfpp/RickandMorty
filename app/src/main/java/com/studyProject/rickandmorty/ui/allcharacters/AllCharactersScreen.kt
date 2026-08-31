package com.studyProject.rickandmorty.ui.allcharacters

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
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.ui.character.CharacterUiState
import com.studyProject.rickandmorty.ui.character.CharacterViewModel
import com.studyProject.rickandmorty.ui.common.CharacterCellSize
import com.studyProject.rickandmorty.ui.common.CharacterGrid as SharedCharacterGrid
import com.studyProject.rickandmorty.ui.common.ErrorContent
import com.studyProject.rickandmorty.ui.common.LoadingContent
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun AllCharactersScreen(
    onBackClick: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()

    AllCharactersContent(
        state = state,
        isLoadingMore = isLoadingMore,
        onLoadMore = viewModel::loadMore,
        onBackClick = onBackClick,
        onCharacterClick = onCharacterClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AllCharactersContent(
    state: CharacterUiState,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit,
    onBackClick: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val background = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Characters",
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
            CharacterUiState.Loading -> LoadingContent(contentModifier)
            is CharacterUiState.Loaded -> AllCharactersGrid(
                characters = state.characters,
                isLoadingMore = isLoadingMore,
                onLoadMore = onLoadMore,
                onCharacterClick = onCharacterClick,
                modifier = contentModifier,
            )
            is CharacterUiState.Error -> ErrorContent(state.message, contentModifier)
        }
    }
}

@Composable
private fun AllCharactersGrid(
    characters: List<Character>,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit,
    onCharacterClick: (Int) -> Unit,
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

    SharedCharacterGrid(
        characters = characters,
        onCharacterClick = onCharacterClick,
        modifier = modifier,
        size = CharacterCellSize.Grid,
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
private fun AllCharactersContentPreview() {
    RickAndMortyTheme {
        AllCharactersContent(
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
            isLoadingMore = false,
            onLoadMore = {},
            onBackClick = {},
            onCharacterClick = {},
        )
    }
}
