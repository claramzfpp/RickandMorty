package com.studyProject.rickandmorty.data.repository

import com.studyProject.rickandmorty.data.remote.RetrofitClient
import com.studyProject.rickandmorty.data.remote.RickAndMortyApi
import com.studyProject.rickandmorty.data.remote.dto.RMCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// camada entre o ViewModel e o networking.
// ele é a FONTE ÚNICA DA VERDADE: o flow de personagens vive aqui.
class CharacterRepository(
    // recebe a api por parâmetro, pra dar pra trocar por uma mock em testes
    private val api: RickAndMortyApi = RetrofitClient.api
) {

    // o flow de personagens (par privado/público)
    private val _characters = MutableStateFlow<List<RMCharacter>>(emptyList())
    val characters: StateFlow<List<RMCharacter>> = _characters.asStateFlow()

    // busca e ACUMULA (paginação)
    suspend fun loadCharacters(page: Int) {
        val results = api.fetchingCharacters(name = null, page = page).results
        _characters.value = _characters.value + results
    }

    // busca por nome e SUBSTITUI a lista inteira
    suspend fun searchByName(name: String, page: Int) {
        val results = api.fetchingCharacters(name = name, page = page).results
        _characters.value = results
    }

    companion object {
        // instância COMPARTILHADA — o "static let shared" do Swift.
        // é isso que permite o flow ser reusado por qualquer ViewModel/tela.
        val shared = CharacterRepository()
    }
}
