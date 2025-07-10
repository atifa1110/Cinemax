package com.example.cinemax.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MovieModel
import com.example.core.domain.model.SearchModel
import com.example.core.domain.model.TvShowModel
import com.example.core.domain.usecase.AddMovieToSearchHistoryUseCase
import com.example.core.domain.usecase.DeleteMovieFromSearchHistoryUseCase
import com.example.core.domain.usecase.GetMovieUseCase
import com.example.core.domain.usecase.GetSearchHistoryUseCase
import com.example.core.domain.usecase.GetTvShowUseCase
import com.example.core.domain.usecase.SearchMovieUseCase
import com.example.core.ui.mapper.EventHandler
import com.example.core.ui.mapper.asMediaTypeModel
import com.example.core.ui.mapper.asMovie
import com.example.core.ui.mapper.asSearchModel
import com.example.core.ui.mapper.asTvShow
import com.example.core.ui.mapper.pagingMap
import com.example.core.ui.model.MediaType
import com.example.core.ui.model.Movie
import com.example.core.ui.model.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val isSearching: Boolean = false,
    val searchMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val trendingMovieUiState: TrendingMovieUiState = TrendingMovieUiState(),
    val trendingTvUiState: TrendingTvUiState = TrendingTvUiState(),
    val historyUiState: HistoryUiState = HistoryUiState(),
    val genreUiState: GenreUiState = GenreUiState(),
    val error: Throwable? = null,
    val isOfflineModeAvailable: Boolean = false,
)

data class TrendingTvUiState(
    val trendingTv : List<TvShow> = emptyList(),
    val isTrendingTv: Boolean = false,
)

data class TrendingMovieUiState(
    val trendingMovies : List<Movie> = emptyList(),
    val isTrendingMovie: Boolean = false,
)

data class HistoryUiState(
    val historyMovies : List<Movie> = emptyList(),
    val isHistory : Boolean = false,
)

data class GenreUiState(
    val genres : List<String> = emptyList(),
    val isGenres : Boolean = false,
    val selectedGenre : String = "Adventure"
)

private const val SEARCH_DEBOUNCE_DURATION = 200L


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val addMovieToSearchHistoryUseCase: AddMovieToSearchHistoryUseCase,
    private val deleteMovieFromSearchHistoryUseCase: DeleteMovieFromSearchHistoryUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    private val getTvShowUseCase: GetTvShowUseCase
) : ViewModel(), EventHandler<SearchEvent> {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<SearchEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    override fun onEvent(event: SearchEvent) {
        when(event){
            is SearchEvent.ChangeQuery -> onQueryChange(event.value)
            SearchEvent.Refresh -> onRefresh()
            SearchEvent.Retry -> onRetry()
            SearchEvent.ClearError -> onClearError()
            else -> {}
        }
    }

    init {
        loadContent()
    }

    private fun loadContent() {
        loadMovies(MediaType.Movie.Trending)
        loadTv(MediaType.TvShow.Trending)
        loadSearchHistory()
    }

    private fun onRefresh() {
        viewModelScope.coroutineContext.cancelChildren()
        loadContent()
    }

    private fun onRetry() {
        onClearError()
        onRefresh()
    }

    private fun onClearError() = _uiState.update { it.copy(error = null) }

    private fun onQueryChange(query: String) {
        val isSearching = query.isNotBlank()
        _uiState.update {
            it.copy(query = query, isSearching = isSearching)
        }
        searchDebounced(query)
    }

    private fun searchDebounced(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DURATION)
            search(query)
        }
    }

    private fun search(query: String) = _uiState.update {
        val searchMovies = if (it.isSearching) {
            searchMovieUseCase(query)
                .pagingMap(MovieModel::asMovie)
                .cachedIn(viewModelScope)
        } else {
            emptyFlow()
        }

        it.copy(
            searchMovies = searchMovies
        )
    }

    fun addSearchHistory(movie : Movie) {
        viewModelScope.launch {
            addMovieToSearchHistoryUseCase.invoke(movie.asSearchModel())
            loadSearchHistory()
            _eventFlow.emit(SearchEvent.ShowSnackbar("Success Add to History"))
        }
    }

    fun deleteSearchHistory(id : Int) {
        viewModelScope.launch {
            deleteMovieFromSearchHistoryUseCase.invoke(id)
            _uiState.update { currentState ->
                val updatedList = currentState.historyUiState.historyMovies.filterNot { it.id == id }
                currentState.copy(
                    historyUiState = currentState.historyUiState.copy(
                        historyMovies = updatedList
                    )
                )
            }
            _eventFlow.emit(SearchEvent.ShowSnackbar("Success Delete From History"))
        }
    }

    private fun loadSearchHistory() = viewModelScope.launch {
        getSearchHistoryUseCase.invoke().collect{ response->
            when(response){
                is CinemaxResponse.Loading -> {
                    _uiState.update {
                        it.copy(
                            historyUiState = HistoryUiState(isHistory = true)
                        )
                    }
                }
                is CinemaxResponse.Success -> {
                    val data = response.value.map(SearchModel::asMovie)
                    _uiState.update {
                        it.copy(
                            historyUiState = HistoryUiState(isHistory = false, historyMovies = data)
                        )
                    }
                }
                is CinemaxResponse.Failure -> {
                    _uiState.update {
                        it.copy(
                            historyUiState = HistoryUiState(isHistory = false)
                        )
                    }
                }
            }

        }
    }

    private fun loadMovies(mediaType: MediaType.Movie) = viewModelScope.launch {
        getMovieUseCase(mediaType.asMediaTypeModel()).collect { response ->
            when(response){
                is CinemaxResponse.Loading -> {
                    _uiState.update {
                        it.copy(
                            trendingMovieUiState = TrendingMovieUiState(isTrendingMovie = true)
                        )
                    }
                }
                is CinemaxResponse.Success -> {
                    val trending = response.value.map(MovieModel::asMovie)
                    _uiState.update {
                        it.copy(
                            trendingMovieUiState = TrendingMovieUiState(
                                isTrendingMovie = false,
                                trendingMovies = trending
                            )
                        )
                    }
                }
                is CinemaxResponse.Failure -> {
                    _uiState.update {
                        it.copy(
                            trendingMovieUiState = TrendingMovieUiState(isTrendingMovie = false)
                        )
                    }
                }
            }
        }
    }

    private fun loadTv(mediaType: MediaType.TvShow) = viewModelScope.launch {
        getTvShowUseCase(mediaType.asMediaTypeModel()).collect { response ->
            when(response){
                is CinemaxResponse.Loading -> {
                    _uiState.update {
                        it.copy(
                            trendingTvUiState = TrendingTvUiState(isTrendingTv = true)
                        )
                    }
                }
                is CinemaxResponse.Success -> {
                    val trending = response.value.map(TvShowModel::asTvShow)
                    _uiState.update {
                        it.copy(
                            trendingTvUiState = TrendingTvUiState(
                                isTrendingTv = false,
                                trendingTv = trending
                            )
                        )
                    }
                }
                is CinemaxResponse.Failure -> {
                    _uiState.update {
                        it.copy(
                            trendingTvUiState = TrendingTvUiState(isTrendingTv = false)
                        )
                    }
                }
            }
        }
    }

}