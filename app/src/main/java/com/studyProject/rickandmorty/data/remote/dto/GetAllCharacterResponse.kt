package com.studyProject.rickandmorty.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable // Codable (Swift)
data class GetAllCharacterResponse( //energia do struct
    val info: GetAllCharacterResponseInfo, // val = let | var = var
    val results: List<RMCharacter> // [RMCharacter] (Swift)
)

@Serializable
data class GetAllCharacterResponseInfo (
    val count: Int,
    val pages: Int
)
