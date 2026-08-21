package com.studyProject.rickandmorty.domain.repository

import com.studyProject.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.StateFlow

// Interface (contrato) do repository.
interface CharacterRepository {

    val characters: StateFlow<List<Character>>

    // carrega a PRÓXIMA página e acumula na lista; não faz nada se já chegou ao fim
    suspend fun loadNextPage()

    // busca por nome (primeira página apenas); não afeta a lista paginada acima
    suspend fun searchCharacters(name: String): List<Character>
}
