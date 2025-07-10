package com.example.cinemax.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cinemax.screen.register.RegisterRoute

object RegisterDestination : CinemaxNavigationDestination {
    override val route: String = "register_route"
    override val destination: String = "register_destination"
}

fun NavGraphBuilder.registerGraph(
    onNavigateToLogin : () -> Unit,
) = composable(route = RegisterDestination.route) {
    RegisterRoute(onNavigateToLogin = onNavigateToLogin)
}