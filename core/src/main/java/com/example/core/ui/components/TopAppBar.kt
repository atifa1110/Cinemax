package com.example.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.R
import com.example.core.ui.theme.CinemaxTheme
import com.example.core.ui.theme.Dark
import com.example.core.ui.theme.Grey
import com.example.core.ui.theme.Soft
import com.example.core.ui.theme.White
import com.google.firebase.auth.FirebaseUser

@Composable
fun HomeAppBar(
    user : FirebaseUser?,
    onSeeFavoriteClick : () -> Unit
){
    Row(modifier = Modifier.testTag("HomeTopAppBar")
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box (modifier = Modifier
            .size(40.dp)
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user?.photoUrl.toString())
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.poster_placeholder),
                contentDescription = user?.photoUrl.toString(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.poster_placeholder),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = CircleShape)
            )
        }
        Column(modifier = Modifier
            .weight(1f)
            .padding(start = 16.dp)){
            Text(
                text = "Hello, ${user?.displayName?.lowercase()}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )

            Text(
                text = stringResource(R.string.lets_stream_your_favorite_movie),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Grey
            )
        }

        Box(modifier = Modifier.clickable { onSeeFavoriteClick()}
            .size(32.dp).testTag("WishlistButton")
            .clip(RoundedCornerShape(12.dp))
            .background(Soft),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.Favorite,
                contentDescription = "Wishlist", tint = Color.Red
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTopAppBar(
    @StringRes title: Int,
    isAuthScreen: Boolean,
    onBackButton :  () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            if(isAuthScreen){
                Text(
                    text = stringResource(id = title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        navigationIcon = {
            Row (Modifier.padding(start = 24.dp)){
                IconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Soft), onClick = onBackButton
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        stringResource(id = R.string.back)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Dark,
            navigationIconContentColor = White,
            titleContentColor = White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    @StringRes title: Int,
    onBackButton :  () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.testTag("TopAppBar"),
        title = {
            Text(
                text = stringResource(id = title),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            Row (Modifier.padding(start = 24.dp)){
                IconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Soft), onClick = onBackButton
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        stringResource(id = R.string.back)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Dark,
            navigationIconContentColor = White,
            titleContentColor = White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(
    @StringRes title: Int,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Dark,
            navigationIconContentColor = White,
            titleContentColor = White
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier : Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current
){

    var isExpanded by remember { mutableStateOf(false) }

    // Animate the width of the text field based on the expanded state
    val textFieldWidth by animateDpAsState(
        targetValue = if (isExpanded) 300.dp else 150.dp,
        label = "animatedTextFieldWidth"
    )

    Row(modifier = modifier
        .fillMaxWidth()
        .background(Dark).padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = modifier.width(textFieldWidth).weight(1f).testTag("SearchTextField"),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.type_search),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            maxLines = 1,
            value = query,
            onValueChange = onQueryChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Soft,
                focusedTextColor = White,
                unfocusedTextColor = White,
                focusedLabelColor = Grey,
                unfocusedContainerColor = Soft,
                disabledContainerColor = Soft,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(30.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Grey
                )
            },
        )

        AnimatedVisibility(
            modifier = Modifier.padding(start = 16.dp),
            visible = query.isNotEmpty(),
            enter = fadeIn() + scaleIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Text(
                modifier = Modifier.testTag(stringResource(id = R.string.cancel)).clickable {
                    onQueryChange("")
                    isExpanded = !isExpanded
                },
                text = stringResource(id = R.string.cancel),
                color = White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(
    title : String,
    isWishlist : Boolean,
    containerColor : Color = Color.Transparent,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackButtonClick: () -> Unit,
    addToWishlist: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.testTag("DetailTopAppBar"),
        title = {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Row (Modifier.padding(start = 8.dp,top=16.dp,bottom = 16.dp)){
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Soft),
                    onClick = onBackButtonClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back", tint = White
                    )
                }
            }
        },
        actions = {
            Row (Modifier.padding(end = 8.dp,top=16.dp,bottom = 16.dp)) {
                IconButton(modifier = Modifier.testTag("WishlistButton")
                    .clip(CircleShape)
                    .background(Soft),
                    onClick = addToWishlist
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Wishlist Button",
                        tint = if(isWishlist) Color.Red else Grey,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = Dark,
            titleContentColor = White
        ),
        scrollBehavior = scrollBehavior,
    )
}



@Preview(showBackground = true)
@Composable
fun ProfileTopAppPreview(){
    CinemaxTheme {
        ProfileTopAppBar(title = R.string.profile)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginTopAppPreview(){
    CinemaxTheme{
        AuthTopAppBar(title = R.string.login, isAuthScreen = true, onBackButton = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTopAppPreview(){
    CinemaxTheme {
        SearchTextField(query = "Avatar", onQueryChange = {})
    }
}