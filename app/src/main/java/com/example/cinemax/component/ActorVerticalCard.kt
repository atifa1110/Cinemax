package com.example.cinemax.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Soft
import com.example.core.ui.model.Movie
import com.example.cinemax.R
@Composable
fun ActorVerticalCard(movie: Movie) {
    Column(
        modifier = Modifier.padding(8.dp).clickable {
            Log.d("PersonImage",movie.profilePath.toString())
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.profilePath.toString())
                .crossfade(true)
                .build(),
            contentDescription = movie.profilePath.toString(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.poster_placeholder),
            modifier = Modifier.size(64.dp).clip(CircleShape)
                .background(Soft)
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = movie.title.toString(),
            color = White,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun ActorCardPreview() {
    CinemaxTheme {
        ActorVerticalCard(
           movie = Movie(
               1,
               "John Watt",false,
               "","","",
               emptyList(),0.0,"",
               "","",92
           )
        )
    }
}