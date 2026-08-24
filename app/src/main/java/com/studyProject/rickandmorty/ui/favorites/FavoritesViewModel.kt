package com.studyProject.rickandmorty.ui.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.domain.repository.CharacterRepository
import com.studyProject.rickandmorty.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    val state: StateFlow<FavoritesUiState> = favoriteRepository.observeFavoriteIds()
        .flatMapLatest { ids ->
            flow {
                emit(FavoritesUiState.Loading)
                val characters = resolveCharacters(ids)
                emit(FavoritesUiState.Loaded(characters))
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavoritesUiState.Loading)

    private suspend fun resolveCharacters(ids: Set<Int>): List<Character> = coroutineScope {
        ids.map { id ->
            async {
                try {
                    characterRepository.getCharacterById(id)
                } catch (e: Exception) {
                    Log.e(TAG, "Falhou ao resolver favorito $id: ${e.message}", e)
                    null
                }
            }
        }.awaitAll().filterNotNull()
    }

    companion object {
        private const val TAG = "APICallTag"
    }
}
