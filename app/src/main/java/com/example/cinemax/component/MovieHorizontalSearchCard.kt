package com.example.cinemax.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cinemax.R
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Orange
import com.example.cinemax.ui.theme.Star
import com.example.cinemax.ui.theme.White
import com.example.core.data.network.getGenreName
import com.example.core.ui.model.Movie

@Composable
fun MovieHorizontalSearchCard(
    movie : Movie?,
    onClick: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Box(modifier = Modifier.testTag("MovieHorizontalCard${movie?.id}")
        .fillMaxWidth()
        .background(Dark)
        .padding(16.dp)
        .clickable {
            onClick(movie?.id ?: 0)
        },
        contentAlignment = Alignment.TopEnd
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(112.dp)
                        .height(147.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie?.posterPath.toString())
                            .crossfade(true)
                            .build(),
                        error = painterResource(id = R.drawable.error_image),
                        contentDescription = movie?.posterPath.toString(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.error_image),
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(8.dp)),
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (movie?.posterPath.isNullOrEmpty())
                                        White.copy(0.2f) else Star
                                )
                                .padding(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painterResource(id = R.drawable.star),
                                    contentDescription = "Star",
                                    tint = Orange,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    modifier = Modifier.padding(start = 5.dp),
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
                Column(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Orange)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.premium),
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    }

                    Text(
                        text = movie?.title.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "Year",
                            tint = Grey
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = movie?.releaseDate.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Grey
                        )
                    }
                    Row(modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.clock),
                            contentDescription = "Time",
                            tint = Grey
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        val isMovie = movie?.mediaType?.equals("movie", ignoreCase = true) == true
                        Text(
                            text = if (isMovie) "${movie.runtime} Minutes" else "${movie?.runtime} Seasons",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Grey
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        if (movie?.adult == false) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Color.Transparent)
                                    .border(
                                        BorderStroke(1.dp, BlueAccent),
                                        shape = RoundedCornerShape(3.dp)
                                    )
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.pg),
                                    fontSize = 10.sp,
                                    color = BlueAccent
                                )
                            }
                        }
                    }


                    Row(modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.film),
                            contentDescription = "Category",
                            tint = Grey
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${stringResource(id = getGenreName(movie?.genres))} | ${movie?.mediaType}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Grey
                        )
                    }
                }
            }
            IconButton(
                modifier = Modifier.testTag("DeleteHistoryButton"),
                onClick = { onDelete(movie?.id?:0) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete",
                    tint = White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieSearchPreview(){
    CinemaxTheme {
        MovieHorizontalSearchCard(
            movie = Movie(
                1,
                "John Watt",false,
                "","","",
                emptyList(),0.0,"",
                "","",92
            ),
            onClick = {},
            onDelete = {}
        )
    }
}
