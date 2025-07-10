package com.example.cinemax.screen.available

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinemax.R
import com.example.core.ui.components.ErrorMovie
import com.example.core.ui.theme.Dark

@Composable
fun NotAvailableRoute(
    modifier : Modifier = Modifier
) {
    Column(modifier.fillMaxSize().background(Dark),
        verticalArrangement = Arrangement.Center
    ) {
        ErrorMovie(errorImage = R.drawable.no_wishlist,
            errorTitle = R.string.coming_soon,
            errorDescription = R.string.found_out_soon
        )
    }
}