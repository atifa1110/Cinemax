package com.example.cinemax.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cinemax.screen.home.HomeRoute
import com.example.core.ui.model.MediaType

object HomeDestination : CinemaxNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

fun NavGraphBuilder.homeGraph(
    onNavigateToWishlist : () -> Unit,
    onNavigateToListDestination: (MediaType.Common) -> Unit,
    onNavigateToDetailsDestination: (MediaType.Details) -> Unit,
) = composable(route = HomeDestination.route) {
    HomeRoute(
        onSeeFavoriteClick = onNavigateToWishlist,
        onSeeAllClick = { onNavigateToListDestination(it) },
        onMovieClick = { onNavigateToDetailsDestination ( MediaType.Details.Movie(it))},
        onTvShowClick = { onNavigateToDetailsDestination (MediaType.Details.TvShow(it))}
    )
}