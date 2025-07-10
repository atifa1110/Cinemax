package com.example.cinemax.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.SubcomposeAsyncImageScope
import com.example.cinemax.ui.theme.Soft

@Composable
fun CinemaxNetworkImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = model,
        contentDescription = contentDescription,
        contentScale = contentScale
    ) { SubcomposeAsyncImageHandler() }
}

@Composable
private fun SubcomposeAsyncImageScope.SubcomposeAsyncImageHandler() {
    when (painter.state) {
        is AsyncImagePainter.State.Loading -> CinemaxImagePlaceholder()
        is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
        AsyncImagePainter.State.Empty, is AsyncImagePainter.State.Error -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Soft)
        )
    }
}