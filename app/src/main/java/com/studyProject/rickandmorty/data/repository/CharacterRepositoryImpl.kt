package com.studyProject.rickandmorty.data.repository

import com.studyProject.rickandmorty.data.mapper.toDomain
import com.studyProject.rickandmorty.data.remote.RickAndMortyApi
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
) : CharacterRepository {
    // implementação do nosso protocol (interface/contrato)
    // o @Inject é um modo de injeção de dependência
    // o hilt controla a construção disso

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    override val characters: StateFlow<List<Character>> = _characters.asStateFlow()

    // próxima página a buscar; null quando não há mais páginas
    private var nextPage: Int? = 1

    override suspend fun loadNextPage() {
        val page = nextPage ?: return // já carregou tudo -> sai

        val response = api.fetchingCharacters(name = null, page = page)
        _characters.value = _characters.value + response.results.map { it.toDomain() }

        // info.pages = total de páginas. Se ainda há próxima, avança; senão, marca o fim.
        nextPage = if (page < response.info.pages) page + 1 else null
    }

    override suspend fun searchCharacters(name: String): List<Character> {
        return try {
            api.fetchingCharacters(name = name, page = 1).results.map { it.toDomain() }
        } catch (e: HttpException) {
            // a API retorna 404 quando nenhum personagem bate com o nome buscado
            if (e.code() == 404) emptyList() else throw e
        }
    }

    override suspend fun getCharacterById(id: Int): Character {
        return api.fetchingCharacter(id).toDomain()
    }
}
