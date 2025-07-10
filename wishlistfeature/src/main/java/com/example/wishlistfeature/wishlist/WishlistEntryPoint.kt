package com.example.wishlistfeature.wishlist

import androidx.compose.runtime.Composable
import com.example.cinemax.screen.wishlist.WishlistViewModel

object WishlistEntryPoint {
    @JvmStatic
    fun getComposable(
        viewModel: WishlistViewModel,
        onMovieClick: (Int) -> Unit
    ): @Composable () -> Unit {
        return {
            WishlistRoute(viewModel = viewModel, onMovieClick = onMovieClick)
        }
    }
}
