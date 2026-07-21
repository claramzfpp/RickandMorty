package com.studyProject.rickandmorty.data.repository

import com.studyProject.rickandmorty.data.remote.RetrofitClient
import com.studyProject.rickandmorty.data.remote.RickAndMortyApi
import com.studyProject.rickandmorty.data.remote.dto.RMCharacter

// camada entre o ViewModel e o networking (padrão em Android)
// ViewModel pede dados aqui e NÃO precisa saber que existe Retrofit
class CharacterRepository(
    // recebe a api por parâmetro, pra dar pra trocar por uma mock em testes
    private val api: RickAndMortyApi = RetrofitClient.api
) {

    // devolve só a lista (o ".results").
    suspend fun getCharacters(page: Int): List<RMCharacter> =
        api.fetchingCharacters(name = null, page = page).results

    suspend fun getCharactersByName(name: String, page: Int): List<RMCharacter> =
        api.fetchingCharacters(name = name, page = page).results
}
