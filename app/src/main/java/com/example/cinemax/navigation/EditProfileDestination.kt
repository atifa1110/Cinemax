package com.example.cinemax.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cinemax.screen.available.NotAvailableRoute

object EditProfileDestination : CinemaxNavigationDestination {
    override val route = "edit_profile_route"
    override val destination = "edit_profile_destination"
}

fun NavGraphBuilder.editProfileGraph(
    onBackButtonClick: () -> Unit,
) = composable(route = EditProfileDestination.route) {
//    EditProfileRoute(
//        onBackButtonClick = onBackButtonClick
//    )
    NotAvailableRoute()
}
