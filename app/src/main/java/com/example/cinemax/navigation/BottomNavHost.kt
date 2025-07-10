package com.example.cinemax.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core.ui.model.MediaType

@Composable
fun BottomNavHost(
    navController: NavHostController,
    startDestination: CinemaxNavigationDestination,
    onNavigateToAuth : () -> Unit,
    onNavigateToDetail: (MediaType.Details) -> Unit,
    onNavigateToList: (MediaType.Common) -> Unit,
    onNavigateToWishlist : () -> Unit,
    onNavigateToEditProfile : () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        homeGraph(
            onNavigateToWishlist = {
                onNavigateToWishlist()
            },
            onNavigateToListDestination = {
                onNavigateToList(it)
            },
            onNavigateToDetailsDestination = {
                onNavigateToDetail(it)
            }
        )
        searchGraph(
            onNavigateToDetailsDestination = {
                onNavigateToDetail(it)
            },
        )
        downloadGraph()
        profileGraph(
            onNavigateToAuth = onNavigateToAuth ,
            onNavigateToEditProfile = onNavigateToEditProfile
        )
    }
}