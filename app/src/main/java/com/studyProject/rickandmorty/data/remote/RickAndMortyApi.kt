package com.studyProject.rickandmorty.data.remote

import com.studyProject.rickandmorty.data.remote.dto.GetAllCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi { // protocol (Swift)

    @GET("character")
    suspend fun fetchingCharacters( // async (Swift)
        @Query("name") name: String?,
        @Query("page") page: Int?
    ): GetAllCharacterResponse
}