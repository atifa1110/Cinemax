package com.example.cinemax.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinemax.component.MoviesPagingContainer
import com.example.cinemax.component.MoviesUpcomingPagingContainer
import com.example.core.ui.components.TopAppBar
import com.example.cinemax.component.TvShowPagingContainer
import com.example.cinemax.ui.theme.Dark
import com.example.core.ui.model.MediaType

@Composable
fun ListRoute(
    onBackButtonClick: () -> Unit,
    onUpcomingClick:(Int) -> Unit,
    onMovieClick:(Int) -> Unit,
    onTvClick: (Int) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ListScreen(
        uiState = uiState,
        onUpcomingClick = onUpcomingClick,
        onMovieClick = onMovieClick,
        onTvClick = onTvClick,
        onBackButtonClick = onBackButtonClick
    )
}
@Composable
fun ListScreen(
    uiState: ListUiState,
    onBackButtonClick: () -> Unit,
    onUpcomingClick: (Int) ->Unit,
    onMovieClick:(Int) -> Unit,
    onTvClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = uiState.mediaType.asTitleResourceId(),
                onBackButton = onBackButtonClick
            )
        }
    ) {
        ListContent(
            uiState = uiState,
            onUpcomingClick = onUpcomingClick,
            onMovieClick = onMovieClick,
            onTvClick = onTvClick,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun ListContent(
    uiState: ListUiState,
    onUpcomingClick: (Int) -> Unit,
    onMovieClick:(Int) -> Unit,
    onTvClick: (Int) -> Unit,
    modifier: Modifier
) {

    val movies = uiState.movies.collectAsLazyPagingItems()
    val tvs = uiState.tvs.collectAsLazyPagingItems()

    Column(modifier = modifier
        .fillMaxSize()
        .background(Dark)
    ) {
        when (uiState.mediaType) {
            MediaType.Common.Movie.Upcoming -> {
                MoviesUpcomingPagingContainer(
                    movies = movies, onClick = onUpcomingClick,
                    modifier = Modifier.testTag("UpcomingMoviesList") // ✅ Test tag
                )
            }

            MediaType.Common.Movie.Popular -> {
                MoviesPagingContainer(
                    movies = movies, onClick = onMovieClick, onHistory = {},
                    modifier = Modifier.testTag("PopularMoviesList") // ✅ Test tag
                )
            }

            MediaType.Common.Movie.NowPlaying -> {
                MoviesPagingContainer(
                    movies = movies, onClick = onMovieClick, onHistory = {},
                    modifier = Modifier.testTag("NowPlayingMoviesList") // ✅ Test tag
                )
            }

            MediaType.Common.TvShow.Popular -> {
                TvShowPagingContainer(
                    tvs = tvs, onClick = onTvClick, onHistory = {},
                    modifier = Modifier.testTag("PopularTvShowList") // ✅ Test tag
                )
            }

            MediaType.Common.TvShow.NowPlaying -> {
                TvShowPagingContainer(
                    tvs = tvs, onClick = onTvClick, onHistory = {},
                    modifier = Modifier.testTag("NowPlayingTvShowList") // ✅ Test tag
                )
            }

            else -> {}
        }
    }
}
