package com.studyProject.rickandmorty

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

enum class ViewState {
    LOADING,
    LOADED,
    ERROR
}

class CharacterViewModel : ViewModel() { // ObservableObject (SwiftUI)

    private val TAG = "APICallTag" // filtre por essa tag no Logcat para ver o que voltou || Log
    private var pageNumber: Int = 1
    private val someCharacters = mutableListOf<RMCharacter>()
    private var viewState = ViewState.LOADING


    init { // igual o init de swift
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch { // estilo Task (Swift)
            try { // try catch = do catch (Swift)
                val response = RetrofitClient.api.fetchingCharacters(name = null, page = pageNumber)

                Log.d(TAG, "OK! Total de personagens: ${response.info.count}")

                someCharacters.addAll(response.results)
                viewState = ViewState.LOADED

            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                viewState = ViewState.ERROR
            }
        }
    }

    fun fetchByName(name: String) {
        viewModelScope.launch {
            try {
                someCharacters.clear()

                val response = RetrofitClient.api.fetchingCharacters(name = name, page = pageNumber)

                Log.d(TAG, "OK! Total de personagens: ${response.info.count}")

                someCharacters.addAll(response.results)
                viewState = ViewState.LOADED

            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                viewState = ViewState.ERROR
            }
        }
    }
}
