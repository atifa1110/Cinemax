package com.example.core.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
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
import com.example.core.R
import com.example.core.data.network.getGenreName
import com.example.core.ui.model.WishList
import com.example.core.ui.model.Genre
import com.example.core.ui.model.MediaType
import com.example.core.ui.theme.Grey
import com.example.core.ui.theme.Orange
import com.example.core.ui.theme.Soft
import com.example.core.ui.theme.WhiteGrey

@Composable
fun WishlistShimmer(){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Soft
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerEffect(
                Modifier
                    .size(121.dp, 83.dp)
                    .clip(RoundedCornerShape(10.dp)))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ShimmerEffect(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(10.dp)))
                ShimmerEffect(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(12.dp)))
                ShimmerEffect(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(10.dp)))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistShimmerPreview(){
    MaterialTheme {
        WishlistShimmer()
    }
}

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier
) {
    val shimmerColors = listOf(
        Color.Gray.copy(alpha = 0.6f),
        Color.Gray.copy(alpha = 0.2f),
        Color.Gray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )


    Box(
        modifier = modifier
            .background(brush)
    )
}


@Composable
fun WishlistCard(
    mediaType : String,
    movie : WishList?,
    onClick : (Int) -> Unit,
    deleteFromWishlist : (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable {
            onClick(movie?.id?:0)
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Soft
        )
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .width(121.dp)
                    .height(83.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center // Replace with image
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
                    )

                    Box(modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(White.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.Black,
                            modifier = Modifier
                        )
                    }
                }
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp)) {
                    Text(
                        text = stringResource(id = getGenreName(movie?.genres)),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = WhiteGrey,
                        maxLines = 1,
                    )

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = movie?.title.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = mediaType,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Grey
                        )

                        Row(modifier = Modifier.padding(start = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(id = R.drawable.star),
                                contentDescription = "Star",
                                tint = Orange,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                modifier = Modifier.padding(start = 3.dp),
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
            Box(modifier = Modifier.padding(bottom = 8.dp)) {
                IconButton(
                    modifier = Modifier.testTag("DeleteWishlist_${movie?.id?:0}"),
                    onClick = { deleteFromWishlist(movie?.id?:0) }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Delete ${movie?.id?:0}",
                        tint = if (movie?.isWishListed == true) Color.Red else Grey
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistCardPreview(
) {
    MaterialTheme {
            WishlistCard(
                mediaType = "Movie",
                movie = WishList(1,MediaType.Wishlist.Movie,
                    "Avatar Ages: Dreams", listOf(Genre(R.string.adventure),Genre(R.string.action)),4.2,
                    "",true
                ),
                onClick ={},
                deleteFromWishlist = {}
            )

    }
}