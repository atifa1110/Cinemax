package com.example.wishlistfeature.wishlist

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinemax.R
import com.example.cinemax.screen.wishlist.WishlistEvent
import com.example.cinemax.screen.wishlist.WishlistUiState
import com.example.cinemax.screen.wishlist.WishlistViewModel
import com.example.core.ui.components.CinemaxSwipeRefresh
import com.example.core.ui.components.ErrorMovie
import com.example.core.ui.components.TopAppBar
import com.example.core.ui.components.WishlistCard
import com.example.core.ui.components.WishlistShimmer
import com.example.core.ui.model.WishList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WishlistRoute(
    onMovieClick: (Int) -> Unit,
    viewModel: WishlistViewModel
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WishlistEvent.ShowSnackbar -> {
                    val snackBarText = context.getString(event.message)
                    snackBarHostState.showSnackbar(snackBarText)
                }
            }
        }
    }

    WishlistScreen(
        uiState = uiState,
        onRefreshMovies = {},
        onMovieClick = onMovieClick,
        deleteMovieFromWishlist = { id-> viewModel.deleteMovieFromWishlist(id) },
        deleteTvShowFromWishlist = { id-> viewModel.deleteTvShowFromWishlist(id)},
        snackBarHostState = snackBarHostState,
    )
}


@Composable
fun WishlistScreen(
    uiState: WishlistUiState,
    onRefreshMovies: () -> Unit,
    onMovieClick: (Int) -> Unit,
    deleteMovieFromWishlist: (Int) -> Unit,
    deleteTvShowFromWishlist: (Int) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(title = R.string.wishlist, onBackButton ={})
        }
    ){
        WishlistContent(
            wishlist = uiState.wishlist,
            isLoading = uiState.isLoading,
            onRefresh = onRefreshMovies,
            onMovieClick = onMovieClick,
            deleteMovieFromWishlist = deleteMovieFromWishlist,
            deleteTvShowFromWishlist = deleteTvShowFromWishlist,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun WishlistContent(
    wishlist: List<WishList>,
    isLoading: Boolean,
    deleteMovieFromWishlist: (Int) -> Unit,
    deleteTvShowFromWishlist: (Int) -> Unit,
    onRefresh: () -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable () -> Unit = {
        ErrorMovie(errorImage = R.drawable.no_wishlist,
            errorTitle = R.string.cannot_find_movie,
            errorDescription = R.string.find_your_movie
        )
    },
    showPlaceholder : Boolean = isLoading
) {
    if (wishlist.isEmpty() && !isLoading) {
        emptyContent()
    }else {
        CinemaxSwipeRefresh(isRefreshing = isLoading, onRefresh = onRefresh) {
            LazyColumn(
                modifier = modifier.fillMaxSize().testTag("WishlistList"),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                if (showPlaceholder) {
                    if (isLoading) {
                        items(5) {
                            WishlistShimmer()
                        }
                    }
                } else {
                    items(items= wishlist, key = {it.id}) { list ->
                        WishlistCard(
                            mediaType = list.mediaType.name,
                            movie = list,
                            onClick = onMovieClick,
                            deleteFromWishlist = {
                                if(list.mediaType.name == "Movie"){
                                    deleteMovieFromWishlist(it)
                                } else{
                                    deleteTvShowFromWishlist(it)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
