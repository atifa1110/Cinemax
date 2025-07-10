package com.example.cinemax.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinemax.R
import com.example.core.ui.components.CinemaxSwipeRefresh
import com.example.core.ui.components.HomeAppBar
import com.example.cinemax.component.MoviesContainer
import com.example.cinemax.component.SearchHome
import com.example.cinemax.component.TvContainer
import com.example.cinemax.component.UpcomingMoviesContainer
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.core.ui.model.MediaType
import com.example.core.ui.model.Movie
import com.example.core.ui.model.TvShow

@Composable
fun HomeRoute(
    onSeeFavoriteClick: () -> Unit,
    onSeeAllClick: (MediaType.Common) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    viewModel : HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    HomeScreen(
        uiState = uiState,
        onSeeFavoriteClick = onSeeFavoriteClick,
        onSeeAllClick = onSeeAllClick,
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick,
        onRefresh = { viewModel.onEvent(HomeEvent.Refresh) },
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSeeFavoriteClick: () -> Unit,
    onSeeAllClick: (MediaType.Common) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onRefresh : () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            HomeAppBar(
                onSeeFavoriteClick = onSeeFavoriteClick,
                user = uiState.userId
            )
        },
        containerColor = Dark
    ) {
        CinemaxSwipeRefresh(
            isRefreshing = uiState.isLoading,
            onRefresh = onRefresh,
            modifier = Modifier.padding(it)
        ) {
            HomeContent(
                movies = uiState.movies,
                tvShows = uiState.tvShows,
                loading = uiState.loadStates,
                onSeeAllClick = onSeeAllClick,
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick
            )
        }
    }
}

@Composable
fun HomeContent(
    movies: Map<MediaType.Movie, List<Movie>>,
    tvShows: Map<MediaType.TvShow, List<TvShow>>,
    loading : Map<MediaType,Boolean>,
    onSeeAllClick: (MediaType.Common) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier.fillMaxSize().testTag("my_lazy_column"),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ){
        item{
            SearchHome(query = "", onQueryChange = {})
        }
        item{
            UpcomingMoviesContainer(
                movies = movies[MediaType.Movie.Upcoming].orEmpty(),
                isLoading = loading[MediaType.Movie.Upcoming]?:false,
                onSeeAllClick = { onSeeAllClick(MediaType.Common.Movie.Upcoming) },
                onMovieClick = onMovieClick
            )
        }
        
        item{
            MoviesContainer(
                titleResourceId = R.string.most_popular_movie,
                onSeeAllClick = { onSeeAllClick(MediaType.Common.Movie.Popular) },
                movies = movies[MediaType.Movie.Popular].orEmpty(),
                isLoading = loading[MediaType.Movie.Popular]?:false,
                onCardClick = onMovieClick
            )
        }

        item{
            TvContainer(
                titleResourceId = R.string.most_popular_tv ,
                onSeeAllClick = { onSeeAllClick(MediaType.Common.TvShow.Popular) },
                tvShow = tvShows[MediaType.TvShow.Popular].orEmpty(),
                isLoading = loading[MediaType.TvShow.Popular]?:false,
                onCardClick = onTvShowClick
            )
        }

        item{
            MoviesContainer(
                titleResourceId = R.string.now_playing_movie,
                onSeeAllClick = { onSeeAllClick(MediaType.Common.Movie.NowPlaying)},
                movies = movies[MediaType.Movie.NowPlaying].orEmpty(),
                isLoading = loading[MediaType.Movie.NowPlaying]?:false,
                onCardClick = onMovieClick
            )
        }

        item{
            TvContainer(
                titleResourceId = R.string.now_playing_tv,
                onSeeAllClick = { onSeeAllClick(MediaType.Common.TvShow.NowPlaying)},
                tvShow = tvShows[MediaType.TvShow.NowPlaying].orEmpty(),
                isLoading = loading[MediaType.TvShow.NowPlaying]?:false,
                onCardClick = onTvShowClick
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeContentPreview(){
    CinemaxTheme {
        val snackBarHostState = remember { SnackbarHostState() }
        // Sample Movie class for illustration

        //Assuming MediaType.Movie has different genres or categories
        val popularMovieMediaType = MediaType.Movie.Popular
        val upcomingMovieMediaType = MediaType.Movie.Upcoming
        val nowMovieMediaType = MediaType.Movie.NowPlaying

        val popularTvMediaType = MediaType.TvShow.Popular
        val nowPlayingTvMediaType = MediaType.TvShow.NowPlaying

        // Fake map for movies
        val movies: Map<MediaType.Movie, List<Movie>> = mapOf(
            popularMovieMediaType to listOf(getFakeMovie()),
            upcomingMovieMediaType to listOf(getFakeMovie()),
            nowMovieMediaType to listOf(getFakeMovie())
        )

        val tvs: Map<MediaType.TvShow, List<TvShow>> = mapOf(
            popularTvMediaType to listOf(getFakeTvShow()),
            nowPlayingTvMediaType to listOf(getFakeTvShow()),
        )

        // Fake map for load states
        val loadStates: Map<MediaType, Boolean> = mapOf(
            popularMovieMediaType to true, // Loaded
            nowMovieMediaType to false, // Not loaded
            upcomingMovieMediaType to false // Not loaded
        )

        HomeScreen(
            uiState = HomeUiState(
                userId = null,
                movies = movies,
                tvShows = tvs,
                loadStates = loadStates,
                selectedCategory = "All",
                errorMessage = null,
                isOfflineModeAvailable = false
            ),
            onSeeFavoriteClick = {},
            onSeeAllClick = {},
            onMovieClick = {},
            onTvShowClick = {},
            onRefresh = {},
            snackBarHostState = snackBarHostState
        )
    }
}