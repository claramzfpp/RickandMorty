package com.studyProject.rickandmorty.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {

    @Query("SELECT characterId FROM favorite_characters")
    fun observeFavoriteIds(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE characterId = :characterId)")
    fun observeIsFavorite(characterId: Int): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE characterId = :characterId)")
    suspend fun isFavoriteOnce(characterId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavoriteCharacterEntity)

    @Query("DELETE FROM favorite_characters WHERE characterId = :characterId")
    suspend fun delete(characterId: Int)
}
