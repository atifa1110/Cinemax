package com.example.cinemax.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cinemax.screen.main.MainScreen
import com.example.core.ui.model.MediaType

object MainDestination : CinemaxNavigationDestination {
    override val route: String = "main_route"
    override val destination: String = "main_destination"
}

fun NavGraphBuilder.mainGraph(
    onNavigateToLogin : () -> Unit,
    onNavigateToDetail: (MediaType.Details) -> Unit,
    onNavigateToList: (MediaType.Common) -> Unit,
    onNavigateToWishlist : () -> Unit,
    onNavigateToEditProfile : () -> Unit
) = composable(MainDestination.route){
    MainScreen(
        onNavigateToAuth = onNavigateToLogin,
        onNavigateToDetail = onNavigateToDetail,
        onNavigateToList = onNavigateToList,
        onNavigateToWishlist = onNavigateToWishlist,
        onNavigateToEditProfile = onNavigateToEditProfile
    )
}