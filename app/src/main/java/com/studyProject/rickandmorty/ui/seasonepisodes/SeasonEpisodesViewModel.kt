package com.studyProject.rickandmorty.ui.seasonepisodes

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.studyProject.rickandmorty.domain.repository.EpisodeRepository
import com.studyProject.rickandmorty.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonEpisodesViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val seasonNumber = savedStateHandle.toRoute<Screen.SeasonEpisodes>().seasonNumber

    private val _state = MutableStateFlow<SeasonEpisodesUiState>(SeasonEpisodesUiState.Loading)
    val state: StateFlow<SeasonEpisodesUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val episodes = episodeRepository.getEpisodesBySeason(seasonNumber)
                _state.value = SeasonEpisodesUiState.Loaded(
                    season = seasonNumber,
                    episodes = episodes.sortedBy { it.episodeNumber }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                _state.value = SeasonEpisodesUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    companion object {
        private const val TAG = "APICallTag"
    }
}
