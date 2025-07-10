package com.example.cinemax.screen.detail

import androidx.annotation.StringRes

sealed interface DetailsEvent {
    object Refresh : DetailsEvent
    object Retry : DetailsEvent
    object ClearUserMessage : DetailsEvent
    object WishlistMovie : DetailsEvent
    object WishlistTv : DetailsEvent
    data class ShowSnackbar(@StringRes val message: Int) : DetailsEvent
}