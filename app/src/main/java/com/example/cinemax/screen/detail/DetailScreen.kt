package com.example.cinemax.screen.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cinemax.component.LoaderScreen
import com.example.cinemax.component.MovieCastCrewCard
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.R
import com.example.core.ui.components.DetailTopAppBar
import com.example.cinemax.component.MovieDetailsItem
import com.example.cinemax.component.TrailersDetailsItem
import com.example.cinemax.component.TvShowDetailsItem
import com.example.cinemax.screen.home.getFakeDetailMovie
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Orange
import com.example.cinemax.ui.theme.Soft
import com.example.cinemax.ui.theme.WhiteGrey
import com.example.core.data.network.getGenreName
import com.example.core.ui.model.CreditsList
import com.example.core.ui.model.ImagesList
import com.example.core.ui.model.MediaType
import com.example.core.ui.model.MovieDetails
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailRoute(
    onBackButton : () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is DetailsEvent.ShowSnackbar -> {
                    val message = context.getString(event.message)
                    snackBarHostState.showSnackbar(message)
                }

                else -> {}
            }
        }
    }

    DetailScreen (
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        onBackButton = onBackButton,
        addMovieToWishlist = { viewModel.onEvent(DetailsEvent.WishlistMovie) },
        addTvShowToWishlist = { viewModel.onEvent(DetailsEvent.WishlistTv) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    uiState: DetailsUiState,
    snackBarHostState: SnackbarHostState,
    loadingContent: @Composable () -> Unit = {
        LoaderScreen(modifier = Modifier
            .fillMaxSize()
            .background(Dark))
    },
    addMovieToWishlist: () -> Unit,
    addTvShowToWishlist: () -> Unit,
    onBackButton : () -> Unit,
) {
    if(uiState.isLoading){
        loadingContent()
    }else{
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        when (uiState.mediaType) {
            is MediaType.Details.Movie -> {
                MovieDetailsItem(
                    movie = uiState.movie,
                    userMessage = uiState.userMessage,
                    snackBarHostState = snackBarHostState,
                    scrollBehavior = scrollBehavior,
                    onBackButtonClick = onBackButton,
                    addToWishlist = addMovieToWishlist,
                )
            }
            is MediaType.Details.TvShow -> {
                TvShowDetailsItem(
                    tvShow = uiState.tvShow,
                    scrollBehavior = scrollBehavior,
                    snackBarHostState = snackBarHostState,
                    addToWishlist = addTvShowToWishlist,
                    onBackButtonClick = onBackButton,
                )
            }
            is MediaType.Details.Trailers -> {
                TrailersDetailsItem(
                    movie = uiState.movie,
                    scrollBehavior = scrollBehavior,
                    snackBarHostState = snackBarHostState,
                    onBackButtonClick = onBackButton,
                    addToWishlist = addMovieToWishlist,
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    uiState: DetailsUiState,
    snackBarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackButtonClick: () -> Unit,
    addToWishlist : () -> Unit,
    snackBarMessageShown : () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .fillMaxSize()
            .background(Dark)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uiState.movie.posterPath?:"")
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.poster_placeholder),
            contentDescription = uiState.movie.posterPath?:"",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.poster_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .height(545.dp)
                .blur(radius = 10.dp)
        )
        Box(modifier = Modifier
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
                    title = uiState.movie.title,
                    isWishlist = uiState.movie.isWishListed,
                    onBackButtonClick = onBackButtonClick,
                    addToWishlist = addToWishlist,
                    scrollBehavior = scrollBehavior
                )
            },
            containerColor = Color.Transparent,
        ){
            MovieContent(
                movie = uiState.movie,
                scrollBehavior = scrollBehavior,
                modifier = Modifier.padding(it),
            )
        }

        uiState.userMessage?.let { userMessage ->
            val snackBarText = stringResource(userMessage)
            LaunchedEffect(userMessage, snackBarText) {
                snackBarHostState.showSnackbar(snackBarText)
                snackBarMessageShown()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieContent(
    movie : MovieDetails,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier : Modifier = Modifier
){
    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailPoster(poster = movie.posterPath?:"")
        }
        item{
            DetailInfo(
                releaseDate = movie.releaseDate?:"",
                runtime = movie.runtime,
                genre = getGenreName(movie.genres),
                rating = movie.rating.toString()
            )
        }
        item{
            DetailActions(
                colorDownload = Orange,
                colorButton = ButtonDefaults.buttonColors(containerColor = Orange),
                textButton = " Play"
            )
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


@Composable
fun DetailPoster(
    poster : String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier.size(205.dp, 287.dp)) {
            Image(
                painter = painterResource(id = R.drawable.movie),
                contentDescription = "Movie Poster",
                modifier = Modifier.fillMaxSize()
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(poster)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.error_image_1),
                contentDescription = poster,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.error_image_1),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(8.dp)),
            )
        }
    }
}

@Composable
fun DetailInfo(
    releaseDate : String,
    runtime : Int,
    genre : Int,
    rating : String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Icon(painter = painterResource(id = R.drawable.calendar),
            contentDescription = "", tint = Grey)

        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = releaseDate,
            color = Grey,
            fontSize = 14.sp
        )

        Text(text = "|", color = Grey, modifier = Modifier.padding(end = 8.dp))

        Icon(painter = painterResource(id = R.drawable.clock),
            contentDescription ="", tint = Grey)
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "$runtime Minutes",
            color = Grey,
            fontSize = 14.sp
        )

        Text(text = "|", color = Grey, modifier = Modifier.padding(end = 8.dp))

        Icon(painter = painterResource(id = R.drawable.film),
            contentDescription = "",tint = Grey)
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(id = genre),
            color = Grey,
            fontSize = 14.sp
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.star),
            contentDescription = "Rating",
            tint = Orange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = rating,
            color = Orange,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DetailActions(
    colorDownload : Color,
    colorButton: ButtonColors,
    textButton : String
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { /* Handle play action */ },
            colors = colorButton,
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.height(48.dp).testTag(textButton)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = textButton, color = Color.White)
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = { /* Handle download action */ },
            modifier = Modifier
                .size(48.dp)
                .background(Soft, shape = CircleShape)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_download),
                contentDescription = "Download", tint = colorDownload)
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = { /* Handle share action */ },
            modifier = Modifier
                .size(48.dp)
                .background(Soft, shape = CircleShape)
        ) {
            Icon(painter = painterResource(id = R.drawable.share),
                contentDescription = "Share",tint = BlueAccent)
        }
    }
}

@Composable
fun DetailStoryLine(
    storyLine : String
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)) {
        Text(
            text = stringResource(R.string.story_line),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        val text = if (expanded) storyLine else storyLine.take(100)
        val more =  if (expanded) stringResource(R.string.less) else stringResource(R.string.more)

        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = WhiteGrey)) {
                pushStringAnnotation(tag = text, annotation = text)
                append(text)
            }
            withStyle(style = SpanStyle(color = BlueAccent)) {
                pushStringAnnotation(tag = more, annotation = more)
                append(".. $more")
            }
        }

        Text(
            text = annotatedString,
            textAlign = TextAlign.Justify,
            fontSize = 14.sp,
            modifier = Modifier.testTag("ExpandedText")
                .padding(top = 10.dp)
                .clickable { expanded = !expanded }
        )
    }
}

@Composable
fun DetailCastAndCrew(
    credits : CreditsList
) {
    val cast = credits.cast
    val crew = credits.crew

    Column {
        if(cast.isNotEmpty()){
            CastAndCrewContainer(titleResourceId = R.string.cast){
                items(cast){ castItem ->
                    MovieCastCrewCard(
                        profilePath = castItem.profilePath,
                        name = castItem.name,
                        description = castItem.character
                    )
                }
            }
        }
        if(crew.isNotEmpty()){
            CastAndCrewContainer(titleResourceId = R.string.crew){
                items(crew){ crewItem ->
                    MovieCastCrewCard(
                        profilePath = crewItem.profilePath,
                        name = crewItem.name,
                        description = crewItem.job
                    )
                }
            }
        }
    }
}

@Composable
fun MovieGallery(
    images : ImagesList,
) {
    val backdrops = images.backdrops

    if(backdrops.isNotEmpty() == true){
        ImagesContainer(titleResourceId = R.string.gallery){
            items(backdrops.take(10)){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it.filePath)
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.poster_placeholder),
                    contentDescription = it.filePath,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.poster_placeholder),
                    modifier = Modifier.size(130.dp, 100.dp)
                )
            }
        }
    }
}

@Composable
private fun CastAndCrewContainer(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = titleResourceId),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }
}

@Composable
fun ImagesContainer(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
){
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = titleResourceId),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    CinemaxTheme {
        val snackBarHostState = remember { SnackbarHostState() }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Surface {
            DetailContent(
                uiState = DetailsUiState(
                    mediaType = MediaType.Details.from(1,"movie"),
                    movie = getFakeDetailMovie(),
                    isLoading = false,
                    userMessage = null
                ),
                snackBarHostState = snackBarHostState,
                scrollBehavior = scrollBehavior,
                onBackButtonClick = {},
                addToWishlist = {},
                snackBarMessageShown = {}
            )
        }
    }
}
