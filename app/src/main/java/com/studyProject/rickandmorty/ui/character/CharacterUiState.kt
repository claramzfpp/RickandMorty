package com.studyProject.rickandmorty.ui.character

import com.studyProject.rickandmorty.domain.model.Character

// sealed = "enum com valores associados" do Swift
// o DADO mora DENTRO do estado
//   - Loaded já carrega a lista (agora de Character, o modelo de domínio)
//   - Error já carrega a mensagem
sealed interface CharacterUiState {

    data object Loading : CharacterUiState

    data class Loaded(
        val characters: List<Character>
    ) : CharacterUiState

    data class Error(
        val message: String
    ) : CharacterUiState
}
