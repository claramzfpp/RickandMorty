package com.studyProject.rickandmorty.ui.favorites

import com.studyProject.rickandmorty.domain.model.Character

sealed interface FavoritesUiState {

    data object Loading : FavoritesUiState

    data class Loaded(
        val characters: List<Character>
    ) : FavoritesUiState

    data class Error(
        val message: String
    ) : FavoritesUiState
}
