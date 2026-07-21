package com.studyProject.rickandmorty

import retrofit2.http.GET
import retrofit2.http.Query

interface Service { // protocol (Swift)

    @GET("character")
    suspend fun fetchingCharacters( // async (Swift)
        @Query("name") name: String?,
        @Query("page") page: Int?
    ): GetAllCharacterResponse
}