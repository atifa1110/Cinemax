package com.example.cinemax.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.Dark

@Composable
fun LoaderScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(Dark),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.testTag("LoaderScreen"),
            color = BlueAccent
        )
    }
}
