package com.example.cinemax.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cinemax.screen.profile.ProfileRoute

object ProfileDestination : CinemaxNavigationDestination {
    override val route = "profile_route"
    override val destination = "profile_destination"
}

fun NavGraphBuilder.profileGraph(
    onNavigateToAuth : () -> Unit,
    onNavigateToEditProfile : () -> Unit
) = composable(route = ProfileDestination.route) {
    ProfileRoute(
        onNavigateToAuth = onNavigateToAuth,
        onNavigateToEditProfile = onNavigateToEditProfile
    )
}