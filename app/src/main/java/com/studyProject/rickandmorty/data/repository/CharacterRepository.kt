package com.studyProject.rickandmorty.data.repository

import com.studyProject.rickandmorty.data.mapper.toDomain
import com.studyProject.rickandmorty.data.remote.RetrofitClient
import com.studyProject.rickandmorty.data.remote.RickAndMortyApi
import com.studyProject.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Camada entre o ViewModel e o networking.
// É a FONTE ÚNICA DA VERDADE e agora trabalha com o modelo de DOMÍNIO (Character),
// não mais com o DTO — o DTO fica escondido aqui dentro.
class CharacterRepository(
    // recebe a api por parâmetro, pra dar pra trocar por uma mock em testes
    private val api: RickAndMortyApi = RetrofitClient.api
) {

    // o flow agora é de Character (domínio), não de RMCharacter (DTO)
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters.asStateFlow()

    // busca e ACUMULA (paginação). Mapeia DTO -> domínio logo após receber.
    suspend fun loadCharacters(page: Int) {
        val results = api.fetchingCharacters(name = null, page = page).results
            .map { it.toDomain() }
        _characters.value = _characters.value + results
    }

    // busca por nome e SUBSTITUI a lista inteira
    suspend fun searchByName(name: String, page: Int) {
        val results = api.fetchingCharacters(name = name, page = page).results
            .map { it.toDomain() }
        _characters.value = results
    }

    companion object {
        // instância COMPARTILHADA — o "static let shared" do Swift
        val shared = CharacterRepository()
    }
}
