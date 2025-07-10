package com.example.cinemax.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MovieModel
import com.example.core.domain.model.TvShowModel
import com.example.core.domain.usecase.GetMovieUseCase
import com.example.core.domain.usecase.GetTvShowUseCase
import com.example.core.domain.usecase.GetUserId
import com.example.core.ui.mapper.EventHandler
import com.example.core.ui.mapper.asMediaTypeModel
import com.example.core.ui.mapper.asMovie
import com.example.core.ui.mapper.asTvShow
import com.example.core.ui.model.MediaType
import com.example.core.ui.model.Movie
import com.example.core.ui.model.TvShow
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val userId : FirebaseUser? = null,
    val movies: Map<MediaType.Movie, List<Movie>> = emptyMap(),
    val tvShows: Map<MediaType.TvShow, List<TvShow>> = emptyMap(),
    val loadStates: Map<MediaType, Boolean> = emptyMap(),
    val errorMessage: String? = null,
    val isOfflineModeAvailable: Boolean = false,
    val selectedCategory : String = "Adventure"
)

internal val HomeUiState.isLoading: Boolean get() = loadStates.values.any { it }

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserId: GetUserId,
    private val getMovieUseCase: GetMovieUseCase,
    private val getTvShowUseCase: GetTvShowUseCase
) : ViewModel(), EventHandler<HomeEvent> {

    private val _uiState = MutableStateFlow(HomeUiState(userId = getUserId()))
    val uiState = _uiState.asStateFlow()

    init {
        loadContent()
    }

    override fun onEvent(event: HomeEvent) = when (event) {
        HomeEvent.Refresh -> onRefresh()
        HomeEvent.Retry -> onRetry()
        HomeEvent.ClearError -> onClearError()
    }

    private fun loadContent() {
        val movieMediaTypes = listOf(
            MediaType.Movie.Upcoming,
            MediaType.Movie.NowPlaying,
            MediaType.Movie.Popular
        )
        movieMediaTypes.forEach(::loadMovies)

        val tvShowMediaTypes = listOf(
            MediaType.TvShow.Popular,
            MediaType.TvShow.NowPlaying
        )
        tvShowMediaTypes.forEach(::loadTvShow)
    }

    private fun onRefresh() {
        viewModelScope.coroutineContext.cancelChildren()
        loadContent()
    }

    private fun onRetry() {
        onClearError()
        onRefresh()
    }

    private fun onClearError() = _uiState.update { it.copy(errorMessage = null) }

    private fun loadMovies(mediaType: MediaType.Movie) = viewModelScope.launch {
        getMovieUseCase(mediaType.asMediaTypeModel()).collect{ response ->
            when(response){
                is CinemaxResponse.Loading -> {
                    _uiState.update {
                        it.copy(
                            loadStates = it.loadStates + (mediaType to true)
                        )
                    }
                }
                is CinemaxResponse.Success -> {
                    val data = response.value
                    _uiState.update {
                        it.copy(
                            movies = it.movies + (mediaType to data.map(MovieModel::asMovie)),
                            loadStates = it.loadStates + (mediaType to false)
                        )
                    }
                }
                is CinemaxResponse.Failure -> {
                    val error = response.error
                    handleFailure(error = error , mediaType = mediaType)
                }
            }
        }
    }

    private fun loadTvShow(mediaType: MediaType.TvShow) = viewModelScope.launch {
        getTvShowUseCase.invoke(mediaType.asMediaTypeModel()).collect{ response ->
            when(response){
                is CinemaxResponse.Loading -> {
                    _uiState.update {
                        it.copy(
                            loadStates = it.loadStates + (mediaType to true)
                        )
                    }
                }
                is CinemaxResponse.Success -> {
                    val data = response.value
                    _uiState.update {
                        it.copy(
                            tvShows = it.tvShows + (mediaType to data.map(TvShowModel::asTvShow)),
                            loadStates = it.loadStates + (mediaType to false)
                        )
                    }
                }
                is CinemaxResponse.Failure -> {
                    handleFailure(error = response.error , mediaType = mediaType)
                }
            }
        }
    }

    private fun handleFailure(error: String, mediaType: MediaType) =
        _uiState.update {
            it.copy(
                errorMessage = error,
                isOfflineModeAvailable = it.movies.values.all(List<Movie>::isNotEmpty),
                loadStates = it.loadStates + (mediaType to false)
            )
        }
}