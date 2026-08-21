package com.studyProject.rickandmorty.domain.repository

import com.studyProject.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.StateFlow

// Interface (contrato) do repository.
interface CharacterRepository { //basicamente uma das abstrações (inversão de dependência)

    val characters: StateFlow<List<Character>>

    // carrega a PRÓXIMA página e acumula na lista; não faz nada se já chegou ao fim
    suspend fun loadNextPage()

    // busca por nome (primeira página apenas); não afeta a lista paginada acima
    suspend fun searchCharacters(name: String): List<Character>

    // busca um personagem específico por id; propaga erro (ex.: 404) se não existir
    suspend fun getCharacterById(id: Int): Character
}
