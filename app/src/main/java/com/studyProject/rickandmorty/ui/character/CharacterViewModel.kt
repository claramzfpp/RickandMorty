package com.studyProject.rickandmorty.ui.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyProject.rickandmorty.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val state: StateFlow<CharacterUiState> = _state.asStateFlow()

    // true enquanto uma página está sendo carregada.
    // Serve de trava (evita cargas duplicadas) e de sinal pro spinner do rodapé.
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    init {
        // observa o flow do repository e reflete na UI
        viewModelScope.launch {
            repository.characters.collect { characters ->
                if (characters.isNotEmpty()) {
                    Log.d(TAG, "Recebidos: ${characters.size}")
                    _state.value = CharacterUiState.Loaded(characters)
                }
            }
        }
        loadMore() // primeira página
    }

    fun loadMore() {
        if (_isLoadingMore.value) return // já tem uma carga em andamento
        viewModelScope.launch {
            _isLoadingMore.value = true
            try {
                repository.loadNextPage()
            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                // só vira erro de tela cheia se ainda não temos nada exibido
                if (_state.value !is CharacterUiState.Loaded) {
                    _state.value = CharacterUiState.Error(e.message ?: "Erro desconhecido")
                }
            } finally {
                _isLoadingMore.value = false
            }
        }
    }

    companion object {
        private const val TAG = "APICallTag"
    }
}
