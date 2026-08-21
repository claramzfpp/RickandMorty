package com.studyProject.rickandmorty.data.remote.dto

import kotlinx.serialization.SerialName
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

@Serializable
data class RMCharacter (
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val origin: CharacterOrigin,
    val image: String,
    val species: String,
    val gender: String
)

@Serializable
data class CharacterOrigin (
    val name: String
)

@Serializable
enum class CharacterStatus { // Enum (swift)
    @SerialName("unknown") UNKNOWN, 
    @SerialName("Alive") ALIVE,
    @SerialName("Dead") DEAD;

    val text: String
        get() = when (this) {
            ALIVE -> "Alive"
            DEAD -> "Dead"
            UNKNOWN -> "Unknown"
        }
}

