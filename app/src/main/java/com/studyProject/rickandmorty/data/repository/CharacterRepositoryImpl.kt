package com.studyProject.rickandmorty.data.repository

import com.studyProject.rickandmorty.data.mapper.toDomain
import com.studyProject.rickandmorty.data.remote.RickAndMortyApi
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// Implementação concreta da interface, na camada data.
// É esta que usa Retrofit. Poderia existir outra (mock, cache) sem a UI saber.
// @Inject constructor = o Hilt sabe como criar isto (injetando a api).
class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi
) : CharacterRepository {

    // o flow de personagens, já no modelo de domínio (Character)
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    override val characters: StateFlow<List<Character>> = _characters.asStateFlow()

    // busca e ACUMULA (paginação). Mapeia DTO -> domínio logo após receber.
    override suspend fun loadCharacters(page: Int) {
        val results = api.fetchingCharacters(name = null, page = page).results
            .map { it.toDomain() }
        _characters.value = _characters.value + results
    }

    // busca por nome e SUBSTITUI a lista inteira
    override suspend fun searchByName(name: String, page: Int) {
        val results = api.fetchingCharacters(name = name, page = page).results
            .map { it.toDomain() }
        _characters.value = results
    }
}
