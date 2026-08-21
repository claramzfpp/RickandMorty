package com.studyProject.rickandmorty.ui.characterdetail

import com.studyProject.rickandmorty.domain.model.Character

sealed interface CharacterDetailUiState {

    data object Loading : CharacterDetailUiState

    data class Loaded(
        val character: Character
    ) : CharacterDetailUiState

    data class Error(
        val message: String
    ) : CharacterDetailUiState
}
