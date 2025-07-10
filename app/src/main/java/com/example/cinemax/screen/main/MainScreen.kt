package com.example.cinemax.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cinemax.navigation.BottomNavHost
import com.example.cinemax.navigation.NavigationBottomBar
import com.example.cinemax.navigation.TopLevelDestination
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.core.ui.model.MediaType

@Composable
fun MainScreen(
    onNavigateToAuth : () -> Unit,
    onNavigateToDetail : (MediaType.Details) -> Unit,
    onNavigateToList: (MediaType.Common) -> Unit,
    onNavigateToWishlist : () -> Unit,
    onNavigateToEditProfile : () -> Unit,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val topLevelDestinations = TopLevelDestination.entries.toTypedArray()
    val startDestination = TopLevelDestination.Home

    Scaffold (
        bottomBar = {
            NavigationBottomBar(
                items = topLevelDestinations,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    ){
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            BottomNavHost(
                navController = navController,
                startDestination = startDestination,
                onNavigateToAuth = onNavigateToAuth,
                onNavigateToDetail = onNavigateToDetail,
                onNavigateToList = onNavigateToList,
                onNavigateToWishlist = onNavigateToWishlist,
                onNavigateToEditProfile = onNavigateToEditProfile
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    CinemaxTheme {
        MainScreen(
            onNavigateToAuth = {},
            onNavigateToDetail = {},
            onNavigateToList = {},
            onNavigateToWishlist = {},
            onNavigateToEditProfile = {}
        )
    }
}