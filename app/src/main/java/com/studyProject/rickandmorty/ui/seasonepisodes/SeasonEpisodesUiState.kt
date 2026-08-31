package com.studyProject.rickandmorty.ui.seasonepisodes

import com.studyProject.rickandmorty.domain.model.Episode

sealed interface SeasonEpisodesUiState {

    data object Loading : SeasonEpisodesUiState

    data class Loaded(
        val season: Int,
        val episodes: List<Episode>
    ) : SeasonEpisodesUiState

    data class Error(
        val message: String
    ) : SeasonEpisodesUiState
}
