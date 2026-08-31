package com.studyProject.rickandmorty.ui.locationdetail

import com.studyProject.rickandmorty.domain.model.Location

sealed interface LocationDetailUiState {

    data object Loading : LocationDetailUiState

    data class Loaded(
        val location: Location
    ) : LocationDetailUiState

    data class Error(
        val message: String
    ) : LocationDetailUiState
}
