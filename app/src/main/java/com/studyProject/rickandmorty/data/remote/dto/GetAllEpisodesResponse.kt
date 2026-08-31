package com.studyProject.rickandmorty.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetAllEpisodesResponse(
    val info: GetAllEpisodesResponseInfo,
    val results: List<RMEpisode>
)

@Serializable
data class GetAllEpisodesResponseInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
