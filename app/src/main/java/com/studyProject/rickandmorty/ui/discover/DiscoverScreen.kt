package com.studyProject.rickandmorty.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = hiltViewModel(),
) {
    // collectAsStateWithLifecycle = observa o StateFlow respeitando o ciclo de vida da tela
    val state by viewModel.state.collectAsStateWithLifecycle()
    DiscoverContent(state = state, modifier = modifier)
}

// "sem estado" (stateless): só recebe o state e desenha
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiscoverContent(
    state: CharacterUiState,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val background = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { DiscoverTitle() },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = background,
                    scrolledContainerColor = background,
                ),
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
            is CharacterUiState.Loaded -> CharacterGrid(state.characters, contentModifier)
            is CharacterUiState.Error -> ErrorContent(state.message, contentModifier)
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
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        // key = { it.id } ajuda o Compose a reaproveitar os itens de forma eficiente
        items(characters, key = { it.id }) { character ->
            DiscoverCharacterCell(character)
        }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorContent(message: String, modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Text(text = message, color = MaterialTheme.colorScheme.onBackground)
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
            )
        )
    }
}
