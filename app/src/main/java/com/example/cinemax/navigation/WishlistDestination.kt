package com.example.cinemax.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cinemax.screen.available.NotAvailableRoute
import com.example.cinemax.screen.wishlist.WishlistViewModel

object WishlistDestination : CinemaxNavigationDestination {
    override val route = "wishlist_route"
    override val destination = "wishlist_destination"
}

fun NavGraphBuilder.wishlistGraph() = composable(WishlistDestination.route) {
    val viewModel: WishlistViewModel = hiltViewModel()

    val wishlistComposable = try {
        val clazz = Class.forName("com.example.wishlistfeature.wishlist.WishlistEntryPoint")
        val method = clazz.getMethod(
            "getComposable",
            WishlistViewModel::class.java,
            Function1::class.java // onMovieClick
        )
        @Suppress("UNCHECKED_CAST")
        method.invoke(null, viewModel, { id: Int -> /* your logic */ }) as @Composable () -> Unit
    } catch (e: Exception) {
        {
            NotAvailableRoute()
        }
    }

    wishlistComposable()
}
