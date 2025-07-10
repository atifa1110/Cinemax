package com.example.cinemax.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cinemax.screen.search.SearchRoute
import com.example.core.ui.model.MediaType

object SearchDestination : CinemaxNavigationDestination {
    override val route = "search_route"
    override val destination = "search_destination"
}

fun NavGraphBuilder.searchGraph(
    onNavigateToDetailsDestination: (MediaType.Details) -> Unit,
) = composable(route = SearchDestination.route) {
    SearchRoute(
        onTvShowClick = { onNavigateToDetailsDestination(MediaType.Details.TvShow(it)) },
        onMovieClick = { onNavigateToDetailsDestination(MediaType.Details.Movie(it)) },
    )
}