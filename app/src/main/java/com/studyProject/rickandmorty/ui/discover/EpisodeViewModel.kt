package com.studyProject.rickandmorty.ui.discover

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyProject.rickandmorty.domain.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
) : ViewModel() {

    private val _seasonCounts = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val seasonCounts: StateFlow<Map<Int, Int>> = _seasonCounts.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _seasonCounts.value = episodeRepository.getEpisodeCountsBySeason()
            } catch (e: Exception) {
                Log.e(TAG, "Falhou: ${e.message}", e)
                // mantém o mapa vazio; os cards de temporada ficam desabilitados (0 episódios)
            }
        }
    }

    companion object {
        private const val TAG = "APICallTag"
    }
}
