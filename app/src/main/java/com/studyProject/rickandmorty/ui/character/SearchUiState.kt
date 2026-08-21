package com.studyProject.rickandmorty.ui.character

import com.studyProject.rickandmorty.domain.model.Character

sealed interface SearchUiState {

    // consulta vazia: usuário ainda não digitou nada
    data object Idle : SearchUiState

    data object Loading : SearchUiState

    data class Loaded(
        val characters: List<Character>
    ) : SearchUiState

    data class Error(
        val message: String
    ) : SearchUiState
}
