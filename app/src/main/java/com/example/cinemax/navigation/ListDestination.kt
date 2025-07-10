package com.example.cinemax.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinemax.screen.list.ListRoute
import com.example.core.ui.model.MediaType

object ListDestination : CinemaxNavigationDestination {
    override val route = "list_route"
    override val destination = "list_destination"

    const val MEDIA_TYPE_ARGUMENT = "mediaType"
    val routeWithArgument = "$route/{$MEDIA_TYPE_ARGUMENT}"

    fun createNavigationRoute(mediaType: MediaType.Common) = "$route/${mediaType.mediaType}"

    fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): MediaType.Common? {
        val mediaTypeString = savedStateHandle.get<String>(MEDIA_TYPE_ARGUMENT)
        return mediaTypeString?.let { MediaType.Common.from(it) }
    }
}

fun NavGraphBuilder.listGraph(
    onBackButtonClick: () -> Unit,
    onNavigateToDetailsDestination: (MediaType.Details) -> Unit,
) = composable(
    route = ListDestination.routeWithArgument,
    arguments = listOf(
        navArgument(ListDestination.MEDIA_TYPE_ARGUMENT) { type = NavType.StringType },
    )
) {
    ListRoute(
        onBackButtonClick = onBackButtonClick,
        onUpcomingClick = { onNavigateToDetailsDestination(MediaType.Details.Trailers(it)) },
        onMovieClick = { onNavigateToDetailsDestination(MediaType.Details.Movie(it)) },
        onTvClick = { onNavigateToDetailsDestination(MediaType.Details.TvShow(it))}
    )
}
