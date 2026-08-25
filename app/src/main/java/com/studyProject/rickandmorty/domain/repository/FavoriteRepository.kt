package com.studyProject.rickandmorty.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun observeFavoriteIds(): Flow<Set<Int>>

    fun observeIsFavorite(characterId: Int): Flow<Boolean>

    suspend fun toggleFavorite(characterId: Int)
}
