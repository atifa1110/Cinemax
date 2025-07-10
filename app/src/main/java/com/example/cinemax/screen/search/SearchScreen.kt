package com.example.cinemax.screen.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinemax.R
import com.example.cinemax.component.MovieHorizontalSearchCard
import com.example.cinemax.component.MovieShimmerHorizontalCard
import com.example.cinemax.component.MoviesContainer
import com.example.cinemax.component.MoviesPagingContainer
import com.example.core.ui.components.SearchTextField
import com.example.cinemax.component.TvContainer
import com.example.cinemax.screen.home.getFakeMovieList
import com.example.cinemax.screen.home.getFakeMovieList2Model
import com.example.cinemax.screen.home.getFakeMovieListModel
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.core.ui.model.Movie
import com.example.core.ui.model.TvShow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SearchRoute(
    onTvShowClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
){
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchMovies = uiState.searchMovies.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SearchEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message)
                }

                else -> {}
            }
        }
    }

    SearchScreen(
        uiState = uiState,
        searchMovies = searchMovies,
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick,
        addToHistory = { movie -> viewModel.addSearchHistory(movie) },
        deleteFromHistory = { id -> viewModel.deleteSearchHistory(id) },
        onQueryChange = { query-> viewModel.onEvent(SearchEvent.ChangeQuery(query)) },
        snackBarHostState = snackBarHostState
    )

}

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    searchMovies: LazyPagingItems<Movie>,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    addToHistory: (Movie) -> Unit,
    deleteFromHistory: (Int) -> Unit,
    onQueryChange: (String) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ){
        SearchContent(
            uiState = uiState,
            searchMovies = searchMovies,
            onQueryChange = onQueryChange,
            onMovieClick = onMovieClick,
            onTvShowClick = onTvShowClick,
            addToHistory = addToHistory,
            deleteFromHistory = deleteFromHistory,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun SearchContent(
    uiState: SearchUiState,
    searchMovies : LazyPagingItems<Movie>,
    onQueryChange: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    addToHistory: (Movie) -> Unit,
    deleteFromHistory: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .fillMaxSize()
        .background(Dark)
    ){
        SearchTextField(
            query = uiState.query,
            onQueryChange = onQueryChange
        )

        AnimatedContent(targetState = uiState.isSearching, label = "Searching") { isSearching ->
            if(isSearching){
                MoviesPagingContainer(movies = searchMovies, onClick = {}, onHistory = addToHistory)
            }else{
                SuggestionsContent(
                    searchHistory = uiState.historyUiState.historyMovies,
                    isHistory = uiState.historyUiState.isHistory,
                    movies = uiState.trendingMovieUiState.trendingMovies,
                    isTrendingMovie = uiState.trendingMovieUiState.isTrendingMovie,
                    tvShow = uiState.trendingTvUiState.trendingTv,
                    isTrendingTv = uiState.trendingTvUiState.isTrendingTv,
                    deleteFromHistory = deleteFromHistory,
                    onMovieClick = onMovieClick,
                    onTvShowClick = onTvShowClick
                )
            }
        }
    }
}

@Composable
private fun SuggestionsContent(
    searchHistory: List<Movie>,
    isHistory : Boolean,
    movies: List<Movie>,
    tvShow : List<TvShow>,
    isTrendingMovie : Boolean,
    isTrendingTv: Boolean,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    deleteFromHistory: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val shouldShowPlaceholder: Boolean = searchHistory.isEmpty() && isHistory
    LazyColumn(
        modifier = modifier.fillMaxSize().testTag("SuggestionsContentLazyColumn"),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        if(shouldShowPlaceholder){
            items(1){
                MovieShimmerHorizontalCard()
            }
        }else{
            items(searchHistory) { history ->
                MovieHorizontalSearchCard(
                    movie = history,
                    onClick = {},
                    onDelete = deleteFromHistory
                )
            }
        }

        item {
            MoviesContainer(
                titleResourceId = R.string.recommendation_movie,
                onSeeAllClick = { },
                movies = movies,
                isLoading = isTrendingMovie,
                onCardClick = onMovieClick
            )
        }

        item {
            TvContainer(
                titleResourceId = R.string.recommendation_tv,
                onSeeAllClick = {  },
                tvShow = tvShow,
                isLoading = isTrendingTv,
                onCardClick = onTvShowClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview(){
    //Mocked Pager flow
    val mockProducts = getFakeMovieListModel()

    val pager = Pager(PagingConfig(pageSize = 8)) {
        object : PagingSource<Int, Movie>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
                return LoadResult.Page(
                    data = mockProducts,
                    prevKey = null,
                    nextKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null
        }
    }

    // Collect the paging data as LazyPagingItems
    val searchMovies = pager.flow.collectAsLazyPagingItems()

    val snackBarHostState = remember { SnackbarHostState() }
    CinemaxTheme {
           SearchScreen(
               uiState = SearchUiState(query = "Movie",isSearching = true),
               searchMovies = searchMovies,
               onMovieClick = {},
               onTvShowClick = {},
               addToHistory = {},
               deleteFromHistory = {},
               onQueryChange = {},
               snackBarHostState = snackBarHostState
           )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchRecommendedPreview(){
    //Mocked Pager flow
    val mockProducts = getFakeMovieListModel()

    val pager = Pager(PagingConfig(pageSize = 8)) {
        object : PagingSource<Int, Movie>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
                return LoadResult.Page(
                    data = mockProducts,
                    prevKey = null,
                    nextKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null
        }
    }

    // Collect the paging data as LazyPagingItems
    val searchMovies = pager.flow.collectAsLazyPagingItems()

    val snackBarHostState = remember { SnackbarHostState() }
    CinemaxTheme {
            SearchScreen(
                uiState = SearchUiState(
                    query = "",
                    isSearching = false,
                    searchMovies =  emptyFlow(),
                    trendingMovieUiState = TrendingMovieUiState(getFakeMovieList(),false),
                    historyUiState = HistoryUiState(getFakeMovieList2Model(),false)
                ),
                searchMovies = searchMovies,
                onMovieClick = {},
                onTvShowClick = {},
                addToHistory = {},
                deleteFromHistory = {},
                onQueryChange = {},
                snackBarHostState = snackBarHostState
            )
    }
}