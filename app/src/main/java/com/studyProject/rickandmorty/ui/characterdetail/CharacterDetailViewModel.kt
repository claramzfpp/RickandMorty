package com.studyProject.rickandmorty.ui.characterdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.studyProject.rickandmorty.domain.repository.CharacterRepository
import com.studyProject.rickandmorty.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val characterId = savedStateHandle.toRoute<Screen.CharacterDetail>().characterId

    private val _state = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val state: StateFlow<CharacterDetailUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val character = repository.getCharacterById(characterId)
                _state.value = CharacterDetailUiState.Loaded(character)
            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                _state.value = CharacterDetailUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    companion object {
        private const val TAG = "APICallTag"
    }
}
