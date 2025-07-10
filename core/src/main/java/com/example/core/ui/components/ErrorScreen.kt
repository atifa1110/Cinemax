package com.example.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.ui.theme.CinemaxTheme
import com.example.core.ui.theme.Dark
import com.example.core.ui.theme.Grey
import com.example.core.ui.theme.WhiteGrey


@Composable
fun ErrorMovie(
    @DrawableRes errorImage: Int,
    @StringRes errorTitle: Int,
    @StringRes errorDescription: Int,
){
    Column(modifier = Modifier.fillMaxSize().background(Dark)
        .testTag("ErrorScreen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = errorImage),
            contentDescription = "No Result")
        Text(modifier = Modifier.padding(top = 16.dp).testTag(stringResource(id = errorTitle)),
            text = stringResource(id = errorTitle),
            color= WhiteGrey,textAlign = TextAlign.Center)
        Text(modifier = Modifier.padding(16.dp),
            text = stringResource(id = errorDescription),
            color = Grey, textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun ErrorScreenPreview(){
    CinemaxTheme {
        Surface {
            ErrorMovie(errorImage = R.drawable.no_result,
                errorTitle = R.string.cannot_find_search,
                errorDescription = R.string.find_your_movie
            )
        }
    }
}

@Preview
@Composable
fun ErrorDownloadPreview(){
    CinemaxTheme {
        Surface {
            ErrorMovie(errorImage = R.drawable.no_movie,
                errorTitle = R.string.cannot_find_movie,
                errorDescription = R.string.find_your_movie
            )
        }
    }
}

@Preview
@Composable
fun ErrorWishlistPreview(){
   CinemaxTheme {
        Surface {
            ErrorMovie(errorImage = R.drawable.no_wishlist,
                errorTitle = R.string.cannot_find_movie,
                errorDescription = R.string.find_your_movie
            )
        }
    }
}