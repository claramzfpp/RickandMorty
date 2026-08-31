package com.studyProject.rickandmorty.data.repository

import com.studyProject.rickandmorty.data.mapper.toDomain
import com.studyProject.rickandmorty.data.remote.RickAndMortyApi
import com.studyProject.rickandmorty.domain.model.Location
import com.studyProject.rickandmorty.domain.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
) : LocationRepository {

    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    override val locations: StateFlow<List<Location>> = _locations.asStateFlow()

    private var nextPage: Int? = 1

    override suspend fun loadNextPage() {
        val page = nextPage ?: return

        val response = api.fetchingLocations(name = null, page = page)
        _locations.value = _locations.value + response.results.map { it.toDomain() }

        nextPage = if (page < response.info.pages) page + 1 else null
    }

    override suspend fun searchLocations(name: String): List<Location> {
        return try {
            api.fetchingLocations(name = name, page = 1).results.map { it.toDomain() }
        } catch (e: HttpException) {
            if (e.code() == 404) emptyList() else throw e
        }
    }

    override suspend fun getLocationById(id: Int): Location {
        return api.fetchingLocation(id).toDomain()
    }
}
