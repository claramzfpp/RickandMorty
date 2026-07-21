package com.studyProject.rickandmorty.ui.character

import com.studyProject.rickandmorty.data.remote.dto.RMCharacter

// sealed = "enum com valores associados" do Swift
// o DADO mora DENTRO do estado
//   - loaded já carrega a lista
//   - error já carrega a mensagem

sealed interface CharacterUiState {

    data object Loading : CharacterUiState

    data class Loaded(
        val characters: List<RMCharacter>
    ) : CharacterUiState

    data class Error(
        val message: String
    ) : CharacterUiState
}
