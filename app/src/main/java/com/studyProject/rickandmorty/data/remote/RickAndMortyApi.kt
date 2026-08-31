package com.studyProject.rickandmorty.data.remote

import com.studyProject.rickandmorty.data.remote.dto.GetAllCharacterResponse
import com.studyProject.rickandmorty.data.remote.dto.GetAllEpisodesResponse
import com.studyProject.rickandmorty.data.remote.dto.GetAllLocationResponse
import com.studyProject.rickandmorty.data.remote.dto.RMCharacter
import com.studyProject.rickandmorty.data.remote.dto.RMLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi { // protocol (Swift)

    @GET("character")
    suspend fun fetchingCharacters( // async (Swift)
        @Query("name") name: String?,
        @Query("page") page: Int?
    ): GetAllCharacterResponse

    @GET("character/{id}")
    suspend fun fetchingCharacter(@Path("id") id: Int): RMCharacter

    @GET("location")
    suspend fun fetchingLocations(
        @Query("name") name: String?,
        @Query("page") page: Int?
    ): GetAllLocationResponse

    @GET("location/{id}")
    suspend fun fetchingLocation(@Path("id") id: Int): RMLocation

    @GET("episode")
    suspend fun fetchingEpisodes(@Query("page") page: Int): GetAllEpisodesResponse
}