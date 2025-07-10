package com.example.cinemax.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cinemax.ui.theme.Soft
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun CinemaxImagePlaceholder(
    modifier: Modifier = Modifier,
    color: Color = Soft
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .cinemaxPlaceholder(color = color)
    )
}

fun Modifier.cinemaxPlaceholder(color: Color) = composed {
    placeholder(
        visible = true,
        color = color,
        shape = RoundedCornerShape(16.dp)
    )
}