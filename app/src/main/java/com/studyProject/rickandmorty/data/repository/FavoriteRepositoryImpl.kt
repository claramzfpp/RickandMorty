package com.studyProject.rickandmorty.data.repository

import com.studyProject.rickandmorty.data.local.FavoriteCharacterDao
import com.studyProject.rickandmorty.data.local.FavoriteCharacterEntity
import com.studyProject.rickandmorty.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteCharacterDao,
) : FavoriteRepository {

    override fun observeFavoriteIds(): Flow<Set<Int>> =
        dao.observeFavoriteIds().map { it.toSet() }

    override fun observeIsFavorite(characterId: Int): Flow<Boolean> =
        dao.observeIsFavorite(characterId)

    override suspend fun toggleFavorite(characterId: Int) {
        if (dao.isFavoriteOnce(characterId)) {
            dao.delete(characterId)
        } else {
            dao.insert(FavoriteCharacterEntity(characterId))
        }
    }
}
