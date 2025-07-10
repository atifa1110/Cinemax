package com.example.cinemax.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemax.R
import com.example.core.ui.components.ShimmerEffect
import com.example.cinemax.screen.home.getFakeMovieList
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.White
import com.example.cinemax.ui.theme.WhiteGrey
import com.example.core.ui.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


private const val PlaceholderCount = 20

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun UpcomingMoviesContainer(
    movies: List<Movie>,
    isLoading : Boolean,
    onSeeAllClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val shouldShowPlaceholder = movies.isEmpty() && isLoading
    val pageCount = if (shouldShowPlaceholder) {
        PlaceholderCount
    } else {
        movies.size
    }

    val pagerState = rememberPagerState(pageCount = { pageCount })
    val autoScrollDuration = 5000L

    // Automatically scroll the pager
    LaunchedEffect(pagerState.currentPage) {
        while (isActive && pageCount > 0) {
            delay(autoScrollDuration) // set delay from 1 to one page
            val nextPage = (pagerState.currentPage + 1) % pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    MoviesContainer(
        titleResourceId = R.string.upcoming_movies,
        onSeeAllClick = onSeeAllClick,
        modifier = modifier,
        content = {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) { page ->
                if(shouldShowPlaceholder){
                    UpcomingMovieItemPlaceholder(
                        modifier = Modifier.pagerTransition(
                        pagerState = pagerState,
                        page = page)
                    )
                }else{
                    with(movies[page]) {
                        UpcomingMovieItem(
                            modifier = Modifier.pagerTransition(
                                pagerState = pagerState,
                                page = page
                            ),
                            title = title.toString(),
                            backdropPath = backdropPath,
                            releaseDate = releaseDate,
                            onClick = onMovieClick
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            DefaultHorizontalPagerIndicator(
                modifier = Modifier.padding(horizontal = 16.dp),
                pagerState = pagerState,
                pageCount = pageCount
            )
        }
    )
}

@Composable
private fun UpcomingMovieItemPlaceholder(modifier: Modifier = Modifier) {
    UpcomingMovieItem(
        modifier = modifier,
        title = "",
        backdropPath = "",
        releaseDate = null,
        onClick = {},
        isPlaceholder = true
    )
}

@Composable
private fun UpcomingMovieItem(
    title: String,
    backdropPath: String?,
    releaseDate: String?,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    isPlaceholder: Boolean = false
) {
    if(isPlaceholder){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(154.dp),
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = Dark
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                ShimmerEffect(Modifier.fillMaxSize())
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .align(Alignment.BottomStart),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ShimmerEffect(
                        Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(8.dp)))
                    ShimmerEffect(
                        Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(8.dp)))
                }
            }
        }
    }else{
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(154.dp)
                .clip(shape)
                .clickable { },
            shape = shape
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CinemaxNetworkImage(
                    modifier = Modifier.fillMaxSize(),
                    model = backdropPath,
                    contentDescription = title
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "On $releaseDate",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = WhiteGrey
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun UpcomingMovieContainerPreview(){
    CinemaxTheme {
        Surface(Modifier.fillMaxSize().background(Dark)){
                UpcomingMoviesContainer(
                    movies = getFakeMovieList(),
                    onSeeAllClick = {},
                    isLoading = true,
                    onMovieClick = {}
                )
        }
    }
}