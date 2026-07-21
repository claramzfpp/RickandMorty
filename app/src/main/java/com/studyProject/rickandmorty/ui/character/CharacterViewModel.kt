package com.studyProject.rickandmorty.ui.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyProject.rickandmorty.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() { // ObservableObject (SwiftUI)
    private var pageNumber: Int = 1

    private val repository = CharacterRepository()

    // um estado só — a lista mora dentro de Loaded
    private val _state = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val state: StateFlow<CharacterUiState> = _state.asStateFlow()
    //em Swift, seria "@Published private(set) var state: CharacterUiState"

    init { // igual o init de swift
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch { // estilo Task (Swift)
            // guarda a lista atual ANTES de trocar pra Loading
            val current = (_state.value as? CharacterUiState.Loaded)?.characters ?: emptyList()
            _state.value = CharacterUiState.Loading

            try { // try catch = do catch (Swift)
                // pede a lista pro repository (ele que fala com a rede)
                val results = repository.getCharacters(page = pageNumber)

                Log.d(TAG, "Recebidos: ${results.size}")

                // novo estado Loaded já carregando a lista (antiga + nova página)
                _state.value = CharacterUiState.Loaded(current + results)

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
                val results = repository.getCharactersByName(name = name, page = pageNumber)

                Log.d(TAG, "Recebidos: ${results.size}")

                // substitui a lista inteira pela nova busca (não acumula)
                _state.value = CharacterUiState.Loaded(results)

            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                _state.value = CharacterUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    // constante da classe (convenção Android p/ TAG de Log)
    companion object {
        private const val TAG = "APICallTag"
    }
}
