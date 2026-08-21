package com.studyProject.rickandmorty.ui.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyProject.rickandmorty.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel //ainda não peguei a energia disso aqui, mas funciona para injeção de dependência e envolve ciclo de vida das views
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val state: StateFlow<CharacterUiState> = _state.asStateFlow()

    // true enquanto uma página está sendo carregada.
    // Serve de trava (evita cargas duplicadas) e de sinal pro spinner do rodapé.
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

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

        // espera o usuário parar de digitar (300ms) antes de buscar;
        // flatMapLatest cancela uma busca ainda em andamento se o texto mudar de novo
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    flowOf(SearchUiState.Idle)
                } else {
                    flow {
                        emit(SearchUiState.Loading)
                        emit(SearchUiState.Loaded(repository.searchCharacters(query)))
                    }.catch { e ->
                        Log.e(TAG, "Busca falhou: ${e.message}", e)
                        emit(SearchUiState.Error(e.message ?: "Erro desconhecido"))
                    }
                }
            }
            .onEach { _searchState.value = it }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
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
