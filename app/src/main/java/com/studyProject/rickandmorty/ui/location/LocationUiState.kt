package com.studyProject.rickandmorty.ui.location

import com.studyProject.rickandmorty.domain.model.Location

sealed interface LocationUiState {

    data object Loading : LocationUiState

    data class Loaded(
        val locations: List<Location>
    ) : LocationUiState

    data class Error(
        val message: String
    ) : LocationUiState
}
