package com.studyProject.rickandmorty.data.remote.dto

import kotlinx.serialization.Serializable

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
