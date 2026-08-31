package com.studyProject.rickandmorty.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CharacterStatus { // Enum (swift)
    @SerialName("unknown") UNKNOWN,
    //esse SerialName basicamente faz o mapeamento da API pro nosso enum
    @SerialName("Alive") ALIVE,
    @SerialName("Dead") DEAD;

    val text: String
        get() = when (this) {
            ALIVE -> "Alive"
            DEAD -> "Dead"
            UNKNOWN -> "Unknown"
        }
}
