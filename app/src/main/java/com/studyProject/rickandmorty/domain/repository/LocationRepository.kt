package com.studyProject.rickandmorty.domain.repository

import com.studyProject.rickandmorty.domain.model.Location
import kotlinx.coroutines.flow.StateFlow

interface LocationRepository {

    val locations: StateFlow<List<Location>>

    suspend fun loadNextPage()

    suspend fun searchLocations(name: String): List<Location>

    suspend fun getLocationById(id: Int): Location
}
