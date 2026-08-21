package com.studyProject.rickandmorty.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.ui.character.CharacterUiState
import com.studyProject.rickandmorty.ui.character.CharacterViewModel
import com.studyProject.rickandmorty.ui.character.SearchUiState
import com.studyProject.rickandmorty.ui.common.ErrorContent
import com.studyProject.rickandmorty.ui.common.LoadingContent
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = hiltViewModel(),
    onCharacterClick: (Int) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    DiscoverContent(
        state = state,
        isLoadingMore = isLoadingMore,
        onLoadMore = viewModel::loadMore,
        searchQuery = searchQuery,
        searchState = searchState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onCharacterClick = onCharacterClick,
        modifier = modifier,
    )
}

// "sem estado" (stateless): só recebe os dados e o callback, e desenha
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiscoverContent(
    state: CharacterUiState,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit,
    searchQuery: String,
    searchState: SearchUiState,
    onSearchQueryChanged: (String) -> Unit,
    onCharacterClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val background = MaterialTheme.colorScheme.background
    var shouldShowSearchBar by rememberSaveable { mutableStateOf(false) } //

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
                        IconButton(onClick = { /* do something */
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
            val contentModifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(background)

            // switch (Swift)
            when (state) {
                CharacterUiState.Loading -> LoadingContent(contentModifier)
                is CharacterUiState.Loaded -> CharacterGrid(
                    characters = state.characters,
                    isLoadingMore = isLoadingMore,
                    onLoadMore = onLoadMore,
                    onCharacterClick = onCharacterClick,
                    modifier = contentModifier,
                )
                is CharacterUiState.Error -> ErrorContent(state.message, contentModifier)
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
private fun CharacterGrid(
    characters: List<Character>,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()

    // vira true quando o usuário chega perto do fim da lista.
    // derivedStateOf = recalcula só quando os valores lidos mudam (eficiente).
    val reachedEnd by remember {
        derivedStateOf {
            val lastVisible = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = gridState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - 4 // dentro dos últimos 4 itens
        }
    }

    LaunchedEffect(reachedEnd) {
        if (reachedEnd) onLoadMore()
    }

    LazyVerticalGrid( // como se fosse LazyVStack dentro de uma ScrollView
        state = gridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {


        items(characters, key = { it.id }) { character ->
            DiscoverCharacterCell(character, onClick = { onCharacterClick(character.id) })
        }

        // rodapé: ocupa a LINHA inteira (span = maxLineSpan) e mostra o spinner
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
            isLoadingMore = false,
            onLoadMore = {},
            searchQuery = "",
            searchState = SearchUiState.Idle,
            onSearchQueryChanged = {},
        )
    }
}
