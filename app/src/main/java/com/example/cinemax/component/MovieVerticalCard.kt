package com.example.cinemax.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.ui.model.Movie
import com.example.cinemax.R
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Orange
import com.example.cinemax.ui.theme.Soft
import com.example.cinemax.ui.theme.Star
import com.example.cinemax.ui.theme.White
import com.example.core.data.network.getGenreName
import com.example.core.ui.components.ShimmerEffect

@Composable
fun MovieVerticalCard(
    movie: Movie?,
    onClick : (Int) -> Unit,
    modifier : Modifier = Modifier
) {
    Card(modifier = modifier.width(135.dp)
            .clickable { onClick(movie?.id ?: 0) },
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Soft
        )
    ) {
        Column {
            Box(modifier = Modifier
                    .width(135.dp)
                    .height(170.dp) // Replace with image
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie?.posterPath.toString())
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.poster_placeholder),
                    contentDescription = movie?.posterPath.toString(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.poster_placeholder),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Star)
                            .padding(4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star",
                                tint = Orange,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = movie?.rating.toString(),
                                fontSize = 12.sp,
                                color = Orange,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = movie?.title.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = White
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(id = getGenreName(movie?.genres)),
                    fontSize = 12.sp,
                    color = Grey
                )
            }
        }
    }
}

@Composable
fun MovieShimmerVerticalCard() {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(6.dp))
        .background(Soft)
        .size(135.dp, 231.dp)
    ) {
        Column {
            ShimmerEffect(
                Modifier
                    .width(135.dp)
                    .height(170.dp)
                    .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
            )

            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                ShimmerEffect(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                ShimmerEffect(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
    }
}


@Preview("Light Mode", device = Devices.PIXEL_3)
@Composable
fun MovieVerticalPreview() {
    CinemaxTheme {
        MovieVerticalCard(
            movie = Movie(
                1,
                "John Watt",false,
                "","","",
                emptyList(),0.0,"",
                "","",92
            ),
            onClick = {}
        )
    }
}

