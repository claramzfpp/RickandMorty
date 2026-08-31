package com.studyProject.rickandmorty.domain.model

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episodeCode: String,
    val season: Int,
    val episodeNumber: Int
)
