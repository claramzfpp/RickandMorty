package com.studyProject.rickandmorty.domain.repository

import com.studyProject.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.StateFlow

// Interface (contrato) do repository, no domínio.
// A UI/ViewModel dependem DISTO — não da implementação concreta.
// Assim dá pra trocar a fonte (API real, mock, cache) sem mexer no resto.
interface CharacterRepository {

    val characters: StateFlow<List<Character>>

    suspend fun loadCharacters(page: Int)

    suspend fun searchByName(name: String, page: Int)
}
