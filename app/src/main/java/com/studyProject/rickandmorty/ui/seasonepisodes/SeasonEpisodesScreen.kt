package com.studyProject.rickandmorty.ui.seasonepisodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.studyProject.rickandmorty.domain.model.Episode
import com.studyProject.rickandmorty.ui.common.EpisodeRow
import com.studyProject.rickandmorty.ui.common.ErrorContent
import com.studyProject.rickandmorty.ui.common.LoadingContent
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun SeasonEpisodesScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SeasonEpisodesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SeasonEpisodesContent(
        state = state,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeasonEpisodesContent(
    state: SeasonEpisodesUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background = MaterialTheme.colorScheme.background
    val title = when (state) {
        is SeasonEpisodesUiState.Loaded -> "Season ${state.season}"
        else -> "Episodes"
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
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
            SeasonEpisodesUiState.Loading -> LoadingContent(contentModifier)
            is SeasonEpisodesUiState.Loaded -> EpisodeList(
                episodes = state.episodes,
                modifier = contentModifier,
            )
            is SeasonEpisodesUiState.Error -> ErrorContent(state.message, contentModifier)
        }
    }
}

@Composable
private fun EpisodeList(
    episodes: List<Episode>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(episodes, key = { it.id }) { episode ->
            EpisodeRow(episode = episode)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SeasonEpisodesContentPreview() {
    RickAndMortyTheme {
        SeasonEpisodesContent(
            state = SeasonEpisodesUiState.Loaded(
                season = 1,
                episodes = listOf(
                    Episode(
                        id = 1,
                        name = "Pilot",
                        airDate = "December 2, 2013",
                        episodeCode = "S01E01",
                        season = 1,
                        episodeNumber = 1,
                    ),
                    Episode(
                        id = 2,
                        name = "Lawnmower Dog",
                        airDate = "December 9, 2013",
                        episodeCode = "S01E02",
                        season = 1,
                        episodeNumber = 2,
                    ),
                ),
            ),
            onBackClick = {},
        )
    }
}
