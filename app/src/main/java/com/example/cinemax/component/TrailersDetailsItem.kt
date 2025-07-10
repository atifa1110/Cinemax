package com.example.cinemax.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.cinemax.R
import com.example.cinemax.screen.detail.DetailCastAndCrew
import com.example.cinemax.screen.detail.DetailStoryLine
import com.example.cinemax.screen.detail.MovieGallery
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.White
import com.example.core.data.network.getGenreName
import com.example.core.ui.components.DetailTopAppBar
import com.example.core.ui.model.MovieDetails
import com.example.core.ui.model.VideosList
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrailersDetailsItem(
    movie : MovieDetails,
    scrollBehavior: TopAppBarScrollBehavior,
    snackBarHostState: SnackbarHostState,
    addToWishlist : () -> Unit,
    onBackButtonClick : () -> Unit,
) {
    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            DetailTopAppBar(
                title = stringResource(R.string.trailer),
                isWishlist = movie.isWishListed,
                containerColor = Dark,
                scrollBehavior = scrollBehavior,
                onBackButtonClick = onBackButtonClick,
                addToWishlist = addToWishlist
            )
        },
        containerColor = Dark,
    ){
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item{
                VideoPlayer(video = movie.videos)
            }
            item{
                Column {
                    Text(text = movie.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = White
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "", tint = Grey
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = movie.releaseDate ?: "",
                            color = Grey,
                            fontSize = 14.sp
                        )

                        Text(text = "|", color = Grey, modifier = Modifier.padding(end = 8.dp))

                        Icon(painter = painterResource(id = R.drawable.film),
                            contentDescription = "",tint = Grey)
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = stringResource(id = getGenreName(movie.genres)),
                            color = Grey,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            item{
                DetailStoryLine(storyLine = movie.overview)
            }
            item{
                DetailCastAndCrew(credits = movie.credits)
            }
            item{
                MovieGallery(images = movie.images)
            }
        }
    }
}


@Composable
fun VideoPlayer(video : VideosList?){
    val result = video?.results?.filter { it.site == "YouTube" && it.type == "Teaser" }?: emptyList()
    val key = result.firstOrNull()?.key ?: return

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)) {

        YouTubePlayerComposable(
            videoId = key,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f) // ✅ Aspect ratio 16:9
                .clip(RoundedCornerShape(16.dp))
                .testTag("VideoPlayer")
        )
    }
}

@Composable
fun YouTubePlayerComposable(videoId: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val youTubePlayerView = remember { YouTubePlayerView(context) }

    val isPlayerReady = remember { mutableStateOf(false) }

    // Lifecycle management
    DisposableEffect(Unit) {
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
                isPlayerReady.value = true
            }
        })

        onDispose {
            youTubePlayerView.release()
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { youTubePlayerView },
            modifier = Modifier.matchParentSize()
        )

        // Placeholder loading shimmer
        if (!isPlayerReady.value) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Grey) // Bisa ganti dengan shimmer efek
            )
        }
    }
}
