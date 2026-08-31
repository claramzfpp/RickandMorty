package com.studyProject.rickandmorty.ui.locationdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.studyProject.rickandmorty.domain.repository.LocationRepository
import com.studyProject.rickandmorty.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val repository: LocationRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val locationId = savedStateHandle.toRoute<Screen.LocationDetail>().locationId

    private val _state = MutableStateFlow<LocationDetailUiState>(LocationDetailUiState.Loading)
    val state: StateFlow<LocationDetailUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val location = repository.getLocationById(locationId)
                _state.value = LocationDetailUiState.Loaded(location)
            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                _state.value = LocationDetailUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    companion object {
        private const val TAG = "APICallTag"
    }
}
