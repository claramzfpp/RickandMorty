package com.studyProject.rickandmorty

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ViewState {
    LOADING,
    LOADED,
    ERROR
}

class CharacterViewModel : ViewModel() { // ObservableObject (SwiftUI)

    private val TAG = "APICallTag" // filtre por essa tag no Logcat para ver o que voltou || Log
    private var pageNumber: Int = 1

    // lista de personagens exposta do mesmo jeito (par privado/público)
    private val _characters = MutableStateFlow<List<RMCharacter>>(emptyList())
    val characters: StateFlow<List<RMCharacter>> = _characters.asStateFlow()

    // versão privada e mutável — só o ViewModel muda
    private val _state = MutableStateFlow(ViewState.LOADING)
    // versão pública e só-leitura — a tela só lê
    val state: StateFlow<ViewState> = _state.asStateFlow()
    //em Swift, criariamos um "@Published private(set) var state: ViewState"


    init { // igual o init de swift
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch { // estilo Task (Swift)
            _state.value = ViewState.LOADING

            try { // try catch = do catch (Swift)
                val response = RetrofitClient.api.fetchingCharacters(name = null, page = pageNumber)

                Log.d(TAG, "OK! Total de personagens: ${response.info.count}")

                // cria uma lista ""NOVA"" (antiga + novos) pra o StateFlow avisar a tela
                _characters.value = _characters.value + response.results
                _state.value = ViewState.LOADED

            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                _state.value = ViewState.ERROR
            }
        }
    }

    fun fetchByName(name: String) {
        viewModelScope.launch {
            _state.value = ViewState.LOADING

            try {
                val response = RetrofitClient.api.fetchingCharacters(name = name, page = pageNumber)

                Log.d(TAG, "OK! Total de personagens: ${response.info.count}")

                // substitui a lista inteira pela nova busca
                _characters.value = response.results
                _state.value = ViewState.LOADED

            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                _state.value = ViewState.ERROR
            }
        }
    }
}
