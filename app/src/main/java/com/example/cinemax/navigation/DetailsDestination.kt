package com.example.cinemax.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinemax.screen.detail.DetailRoute
import com.example.core.ui.model.MediaType

object DetailsDestination : CinemaxNavigationDestination {
    override val route = "details_route"
    override val destination = "details_destination"

    const val ID_ARGUMENT = "id"
    const val MEDIA_TYPE_ARGUMENT = "mediaType"

    private const val MEDIA_ID_NULL_MESSAGE = "Media id is null."
    private const val MEDIA_TYPE_NULL_MESSAGE = "Media type is null."

    val routeWithArguments = "$route/{$ID_ARGUMENT}/{$MEDIA_TYPE_ARGUMENT}"

    fun createNavigationRoute(mediaType: MediaType.Details) =
        "$route/${mediaType.mediaId}/${mediaType.mediaType}"

    fun fromSavedStateHandle(savedStateHandle: SavedStateHandle) = MediaType.Details.from(
        id = checkNotNull(savedStateHandle[ID_ARGUMENT]) { MEDIA_ID_NULL_MESSAGE },
        mediaType = checkNotNull(savedStateHandle[MEDIA_TYPE_ARGUMENT]) { MEDIA_TYPE_NULL_MESSAGE }
    )
}

fun NavGraphBuilder.detailsGraph(
    onBackButtonClick: () -> Unit,
) = composable(
    route = DetailsDestination.routeWithArguments,
    arguments = listOf(
        navArgument(DetailsDestination.ID_ARGUMENT) { type = NavType.IntType },
        navArgument(DetailsDestination.MEDIA_TYPE_ARGUMENT) { type = NavType.StringType }
    )
) {
    DetailRoute(
        onBackButton = onBackButtonClick
    )
}

