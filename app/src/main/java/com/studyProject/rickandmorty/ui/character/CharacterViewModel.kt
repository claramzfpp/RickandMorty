package com.studyProject.rickandmorty.ui.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyProject.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// o repository chega pelo CONSTRUTOR (o Koin injeta) — não é mais criado aqui dentro
class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() { // ObservableObject (SwiftUI)

    private var pageNumber: Int = 1

    // estado da UI (loading/loaded/error)
    private val _state = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val state: StateFlow<CharacterUiState> = _state.asStateFlow()

    init {
        // OBSERVA o flow do repository: sempre que a lista muda lá, refletimos aqui.
        viewModelScope.launch {
            repository.characters.collect { characters ->
                if (characters.isNotEmpty()) {
                    Log.d(TAG, "Recebidos: ${characters.size}")
                    _state.value = CharacterUiState.Loaded(characters)
                }
            }
        }
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            _state.value = CharacterUiState.Loading
            try {
                repository.loadCharacters(pageNumber)
            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                _state.value = CharacterUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun fetchByName(name: String) {
        viewModelScope.launch {
            _state.value = CharacterUiState.Loading
            try {
                repository.searchByName(name = name, page = pageNumber)
            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                _state.value = CharacterUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    companion object {
        private const val TAG = "APICallTag"
    }
}
