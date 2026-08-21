package com.studyProject.rickandmorty.ui.character

import com.studyProject.rickandmorty.domain.model.Character

sealed interface SearchUiState {
    /*
    é como se fosse um enum, mas:

    enum class (como que temos no dto) sempre vão carregar o mesmo "tipo"
    como se fosse uma lista homogênea

    já em sealed interface temos uma lista heterogênea
    podendo ter tipos que retornam algo ou não
     */

    // consulta vazia: usuário ainda não digitou nada
    data object Idle : SearchUiState

    data object Loading : SearchUiState //especie de singleton/enum?

    data class Loaded( // uma struct
        val characters: List<Character>
    ) : SearchUiState

    data class Error(
        val message: String
    ) : SearchUiState

    /*
    obs: sempre que uma variante for carregar um objeto,
    deve ser instanciada como data class
    quando não precisa, devemos instanciar como data object
     */
}
