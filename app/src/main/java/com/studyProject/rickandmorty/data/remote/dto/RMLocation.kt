package com.studyProject.rickandmorty.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetAllLocationResponse(
    val info: GetAllLocationResponseInfo,
    val results: List<RMLocation>
)

@Serializable
data class GetAllLocationResponseInfo(
    val count: Int,
    val pages: Int
)

@Serializable
data class RMLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)
