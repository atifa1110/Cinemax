package com.example.cinemax.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun CinemaxNavHost(
    navController: NavHostController,
    startDestination: CinemaxNavigationDestination,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        boardingGraph (
            onNavigateToAuth = {
                navController.navigate(AuthDestination.route){
                    popUpTo(OnBoardingDestination.route){
                        inclusive = true
                    }
                }
            }
        )

        authGraph(
            onNavigateToRegister = {
                navController.navigate(RegisterDestination.route)
            },
            onNavigateToLogin = {
                navController.navigate(LoginDestination.route)
            }
        )

        loginGraph(
            onNavigateToForgotPassword = {},
            onNavigateToHome = {
                navController.navigate(MainDestination.route){
                    popUpTo(AuthDestination.route){ inclusive = true }
                    launchSingleTop = true
                }
            }
        )

        registerGraph (
            onNavigateToLogin = {
                navController.navigate(LoginDestination.route){
                    popUpTo(RegisterDestination.route){
                        inclusive = true
                    }
                }
            }
        )

        mainGraph (
            onNavigateToLogin = {
                navController.navigate(AuthDestination.route){
                    popUpTo(MainDestination.route) {
                        inclusive = true
                    }
                }
            },
            onNavigateToDetail = {
                navController.navigate(DetailsDestination.createNavigationRoute(it))
            },
            onNavigateToList = {
                navController.navigate(ListDestination.createNavigationRoute(it))
            },
            onNavigateToWishlist = {
                navController.navigate(WishlistDestination.route)
            },
            onNavigateToEditProfile = {
                navController.navigate(EditProfileDestination.route)
            }
        )

        detailsGraph(
            onBackButtonClick = onBackButtonClick,
        )

        listGraph(
            onBackButtonClick = onBackButtonClick,
            onNavigateToDetailsDestination = {
                navController.navigate(
                    DetailsDestination.createNavigationRoute(it)
                )
            }
        )
        wishlistGraph()

        editProfileGraph(
            onBackButtonClick = onBackButtonClick
        )
    }
}