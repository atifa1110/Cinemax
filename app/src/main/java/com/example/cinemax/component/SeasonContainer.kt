package com.example.cinemax.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.ui.model.Season
import com.example.cinemax.R
import com.example.core.data.network.getYearReleaseDate

@Composable
fun SeasonCard(
    season: Season
) {
    Row(modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .width(100.dp)
            .height(120.dp) // Replace with image
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(season.posterPath)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.error_image),
                contentDescription = season.posterPath,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.error_image),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(8.dp)),
            )
        }
        Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
        ) {
            // Season title
            Text(
                text = season.name,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rating and episodes
            Text(
                text = "⭐ ${season.rating}% | ${season.airDate.getYearReleaseDate()} • ${season.episodeCount} Episodes",
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Premiere date
            Text(text = "Premiered on: ${season.airDate}", color = Color.White)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SeasonDetailsPreview() {
    val exampleSeason = Season(
        id = 1,
        name = "Season 1",
        seasonNumber = 21,
        overview = "this season is fun",
        rating = "80",
        posterPath = "",
        episodeCount = 9,
        airDate = "March 02, 2024"
    )
    MaterialTheme {
        SeasonCard(season = exampleSeason)
    }
}
