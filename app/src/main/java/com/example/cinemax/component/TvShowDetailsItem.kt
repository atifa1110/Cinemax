package com.example.cinemax.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cinemax.R
import com.example.cinemax.screen.detail.DetailActions
import com.example.cinemax.screen.detail.DetailCastAndCrew
import com.example.cinemax.screen.detail.DetailContent
import com.example.cinemax.screen.detail.DetailInfo
import com.example.cinemax.screen.detail.DetailPoster
import com.example.cinemax.screen.detail.DetailStoryLine
import com.example.cinemax.screen.detail.DetailsUiState
import com.example.cinemax.screen.home.getFakeDetailTvShow
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.core.data.network.getGenreName
import com.example.core.data.network.getYearReleaseDate
import com.example.core.ui.components.DetailTopAppBar
import com.example.core.ui.model.CreditsList
import com.example.core.ui.model.Genre
import com.example.core.ui.model.MediaType
import com.example.core.ui.model.Season
import com.example.core.ui.model.TvShowDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowDetailsItem(
    tvShow : TvShowDetails,
    scrollBehavior: TopAppBarScrollBehavior,
    snackBarHostState: SnackbarHostState,
    addToWishlist : () -> Unit,
    onBackButtonClick : () -> Unit,
) {

    DetailsItemTvShow(
        title = tvShow.name,
        overview = tvShow.overview,
        posterPath = tvShow.posterPath?:"",
        releaseDate = tvShow.firstAirDate?:"",
        runtime = 0,
        genres = tvShow.genres,
        seasons = tvShow.seasons,
        rating = tvShow.rating,
        credits = tvShow.credits,
        isWishlist = tvShow.isWishListed,
        onBackButtonClick = onBackButtonClick,
        addToWishlist = addToWishlist,
        snackBarHostState = snackBarHostState,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsItemTvShow(
    title: String,
    overview: String,
    posterPath: String,
    releaseDate: String,
    runtime: Int,
    genres: List<Genre>,
    seasons : List<Season>,
    rating: Double,
    credits: CreditsList,
    isWishlist: Boolean,
    onBackButtonClick: () -> Unit,
    addToWishlist: () -> Unit,
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    Box(
        modifier
            .fillMaxSize()
            .background(Dark)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(posterPath)
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.poster_placeholder),
            contentDescription = posterPath,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.poster_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .height(545.dp)
                .blur(radius = 10.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1F1D2B).copy(alpha = 0.2f),
                            Color(0xFF1F1D2B).copy(alpha = 1.0f)
                        )
                    )
                )
        )

        Scaffold (
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            topBar = {
                DetailTopAppBar(
                    title = title,
                    isWishlist = isWishlist,
                    onBackButtonClick = onBackButtonClick,
                    addToWishlist = addToWishlist,
                    scrollBehavior = scrollBehavior
                )
            },
            containerColor = Color.Transparent,
        ){
            LazyColumn(
                modifier.testTag("TvShowDetailsItemList")
                    .fillMaxSize()
                    .padding(it)
                    .padding(start = 16.dp, end= 16.dp, bottom = 16.dp)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    DetailPoster(poster = posterPath)
                }
                item{
                    DetailInfo(
                        releaseDate = releaseDate.getYearReleaseDate(),
                        runtime = runtime,
                        genre = getGenreName(genres),
                        rating = rating.toString()
                    )
                }
                item{
                    DetailActions(
                        colorDownload = BlueAccent,
                        colorButton = ButtonDefaults.buttonColors(containerColor = BlueAccent),
                        textButton = " Trailer"
                    )
                }
                item{
                    DetailStoryLine(storyLine = overview)
                }
                item{
                    DetailCastAndCrew(credits = credits)
                }

                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.seasons),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                items(items = seasons){ item ->
                    SeasonCard(season = item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TvShowDetailScreenPreview() {
    CinemaxTheme {
        val snackBarHostState = remember { SnackbarHostState() }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            DetailContent(
                uiState = DetailsUiState(
                    mediaType = MediaType.Details.from(1,"tv_show"),
                    tvShow = getFakeDetailTvShow(),
                    isLoading = false,
                    userMessage = null
                ),
                snackBarHostState = snackBarHostState,
                scrollBehavior = scrollBehavior,
                onBackButtonClick = { /*TODO*/ },
                addToWishlist = { /*TODO*/ },
                snackBarMessageShown = { /*TODO*/ })
    }
}
