package com.studyProject.rickandmorty.ui.location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyProject.rickandmorty.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<LocationUiState>(LocationUiState.Loading)
    val state: StateFlow<LocationUiState> = _state.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    init {
        viewModelScope.launch {
            repository.locations.collect { locations ->
                if (locations.isNotEmpty()) {
                    Log.d(TAG, "Recebidos: ${locations.size}")
                    _state.value = LocationUiState.Loaded(locations)
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
                if (_state.value !is LocationUiState.Loaded) {
                    _state.value = LocationUiState.Error(e.message ?: "Erro desconhecido")
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
