package com.example.cinemax.screen.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinemax.navigation.ListDestination
import com.example.core.domain.model.MovieModel
import com.example.core.domain.model.TvShowModel
import com.example.core.domain.usecase.GetMoviePagingUseCase
import com.example.core.domain.usecase.GetTvPagingUseCase
import com.example.core.ui.mapper.asMediaTypeModel
import com.example.core.ui.mapper.asMovie
import com.example.core.ui.mapper.asMovieMediaType
import com.example.core.ui.mapper.asTvShow
import com.example.core.ui.mapper.asTvShowMediaType
import com.example.core.ui.mapper.pagingMap
import com.example.core.ui.model.MediaType
import com.example.core.ui.model.Movie
import com.example.core.ui.model.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ListUiState(
    val mediaType: MediaType.Common,
    val movies: Flow<PagingData<Movie>>,
    val tvs: Flow<PagingData<TvShow>>,
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getMoviePagingUseCase: GetMoviePagingUseCase,
    private val getTvPagingUseCase: GetTvPagingUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(getInitialUiState(savedStateHandle))
    val uiState = _uiState.asStateFlow()

    private fun getInitialUiState(savedStateHandle: SavedStateHandle): ListUiState {
        val mediaType = ListDestination.fromSavedStateHandle(savedStateHandle)?:MediaType.Common.Movie.Upcoming
        val movies = getMoviePagingUseCase(mediaType.asMovieMediaType().asMediaTypeModel())
            .pagingMap(MovieModel::asMovie)
            .cachedIn(viewModelScope)

        val tvs = getTvPagingUseCase(mediaType.asTvShowMediaType().asMediaTypeModel())
            .pagingMap(TvShowModel::asTvShow).cachedIn(viewModelScope)

        return ListUiState(mediaType = mediaType, movies = movies,tvs = tvs)
   }
}