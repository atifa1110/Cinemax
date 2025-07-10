package com.example.cinemax.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cinemax.screen.login.LoginRoute

object LoginDestination : CinemaxNavigationDestination {
    override val route: String = "login_route"
    override val destination: String = "login_destination"
}

fun NavGraphBuilder.loginGraph(
    onNavigateToForgotPassword : () -> Unit,
    onNavigateToHome : () -> Unit,
) = composable(route = LoginDestination.route) {
    LoginRoute(
        onNavigateToForgotPassword = onNavigateToForgotPassword,
        onNavigateToHome = onNavigateToHome
    )
}