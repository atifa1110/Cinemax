package com.example.cinemax.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemax.R
import com.example.cinemax.navigation.DetailsDestination
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.usecase.AddMovieToWishlistUseCase
import com.example.core.domain.usecase.AddTvShowToWishlistUseCase
import com.example.core.domain.usecase.GetDetailMovieUseCase
import com.example.core.domain.usecase.GetDetailTvShowUseCase
import com.example.core.domain.usecase.RemoveMovieFromWishlistUseCase
import com.example.core.domain.usecase.RemoveTvShowFromWishlistUseCase
import com.example.core.ui.mapper.EventHandler
import com.example.core.ui.mapper.asMovieDetailModel
import com.example.core.ui.mapper.asMovieDetails
import com.example.core.ui.mapper.asTvShowDetailModel
import com.example.core.ui.mapper.asTvShowDetails
import com.example.core.ui.model.MediaType
import com.example.core.ui.model.MovieDetails
import com.example.core.ui.model.TvShowDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiState(
    val mediaType: MediaType.Details,
    val movie: MovieDetails = MovieDetails(),
    val tvShow : TvShowDetails = TvShowDetails(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val errorMessage: String? = null,
    val isOfflineModeAvailable: Boolean = false,
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val addMovieToWishlistUseCase: AddMovieToWishlistUseCase,
    private val removeMovieFromWishlistUseCase: RemoveMovieFromWishlistUseCase,
    private val addTvShowToWishlistUseCase: AddTvShowToWishlistUseCase,
    private val removeTvShowFromWishlistUseCase: RemoveTvShowFromWishlistUseCase,
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getDetailTvShowUseCase: GetDetailTvShowUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), EventHandler<DetailsEvent>{

    private val _uiState = MutableStateFlow(getInitialUiState(savedStateHandle))
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<DetailsEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun getInitialUiState(savedStateHandle: SavedStateHandle): DetailsUiState {
        val mediaType = DetailsDestination.fromSavedStateHandle(savedStateHandle)
        return DetailsUiState(mediaType = mediaType)
    }

    init {
        loadContent()
    }

    override fun onEvent(event: DetailsEvent) = when (event) {
        DetailsEvent.WishlistMovie -> onWishlistMovie()
        DetailsEvent.WishlistTv -> onWishlistTvShow()
        DetailsEvent.Refresh-> onRefresh()
        DetailsEvent.Retry -> onRetry()
        DetailsEvent.ClearUserMessage -> onClearUserMessage()
        else -> {}
    }

    private fun loadContent() = when (val mediaType = uiState.value.mediaType) {
        is MediaType.Details.Movie -> loadMovie(mediaType.id)
        is MediaType.Details.TvShow -> loadTv(mediaType.id)
        is MediaType.Details.Trailers -> loadMovie(mediaType.id)
    }

    private fun onRefresh() {
        viewModelScope.coroutineContext.cancelChildren()
        loadContent()
    }

    private fun onRetry() {
        onClearErrorMessage()
        onRefresh()
    }

    private fun onClearErrorMessage() = _uiState.update { it.copy(errorMessage = null) }
    private fun onClearUserMessage() = _uiState.update { it.copy(userMessage = null) }

    private fun onWishlistMovie() {
        _uiState.update {
            it.copy(movie = it.movie.copy(isWishListed = !it.movie.isWishListed))
        }
        viewModelScope.launch {
            uiState.value.movie.let { movie ->
                if (movie.isWishListed) {
                    addMovieToWishlistUseCase(movie.asMovieDetailModel())
                    _eventFlow.emit(DetailsEvent.ShowSnackbar(R.string.add_wishlist))
                } else {
                    removeMovieFromWishlistUseCase(movie.id)
                    _eventFlow.emit(DetailsEvent.ShowSnackbar(R.string.remove_wishlist))
                }
            }
        }
    }

    private fun onWishlistTvShow() {
        _uiState.update {
            it.copy(tvShow = it.tvShow.copy(isWishListed = !it.tvShow.isWishListed))
        }
        viewModelScope.launch {
            uiState.value.tvShow.let { tvShow ->
                if (tvShow.isWishListed) {
                    addTvShowToWishlistUseCase(tvShow.asTvShowDetailModel())
                    _eventFlow.emit(DetailsEvent.ShowSnackbar(R.string.add_wishlist))
                } else {
                    removeTvShowFromWishlistUseCase(tvShow.id)
                    _eventFlow.emit(DetailsEvent.ShowSnackbar(R.string.remove_wishlist))
                }
            }
        }
    }

    private fun loadTv(id: Int) = viewModelScope.launch {
        getDetailTvShowUseCase.invoke(id).collect{ response ->
            when(response){
                is CinemaxResponse.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is CinemaxResponse.Success -> {
                    _uiState.update {
                        it.copy(
                            tvShow = response.value?.asTvShowDetails()?:TvShowDetails(),
                            isLoading = false
                        )
                    }
                }
                is CinemaxResponse.Failure -> {
                    handleFailure(response.error)
                }
            }
        }
    }

    private fun loadMovie(id: Int) = viewModelScope.launch {
        getDetailMovieUseCase(id).collect{ response ->
            when(response) {
                is CinemaxResponse.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is CinemaxResponse.Success -> {
                    _uiState.update {
                        it.copy(
                            movie = response.value?.asMovieDetails()?: MovieDetails(),
                            isLoading = false
                        )
                    }
                }
                is CinemaxResponse.Failure -> {
                    handleFailure(response.error)
                }
            }
        }
    }

    private fun handleFailure(error: String) = _uiState.update {
        it.copy(
            errorMessage = error,
            isOfflineModeAvailable = it.movie.title.isNotEmpty() || it.tvShow.name.isNotEmpty(),
            isLoading = false
        )
    }

}