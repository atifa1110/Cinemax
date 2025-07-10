package com.example.cinemax.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemax.R
import com.example.cinemax.component.CinemaxButton
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Soft
import com.example.cinemax.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AuthRoute(
    onNavigateToRegister : () -> Unit,
    onNavigateToLogin : () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        AuthScreen(
            modifier = Modifier.padding(paddingValues),
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToLogin = onNavigateToLogin,
            onGoogleClick = { scope.showUnavailableSnackBar(snackbarHostState) },
            onAppleClick = { scope.showUnavailableSnackBar(snackbarHostState) },
            onFacebookClick = { scope.showUnavailableSnackBar(snackbarHostState) }
        )
    }
}

@Composable
fun AuthScreen(
    modifier: Modifier,
    onNavigateToRegister : () -> Unit,
    onNavigateToLogin : () -> Unit,
    onGoogleClick : () -> Unit,
    onAppleClick : () -> Unit,
    onFacebookClick : () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Dark)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(88.dp),
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "Logo"
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.cinemax),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            color = White
        )
        Text(
            text = stringResource(R.string.enter_your_registered_phone_number_to_sign_up),
            textAlign = TextAlign.Center,
            color = Grey
        )

        Spacer(modifier = Modifier.height(50.dp))

        CinemaxButton(
            title = R.string.sign_up,
            onButtonClick = {
                onNavigateToRegister()
            }
        )

        AlreadyHaveAccount(onClick = { onNavigateToLogin() }, isVerification = false)
        OrSignUpButton()
        SocialMediaButton(onGoogleClick = onGoogleClick,
            onAppleClick = onAppleClick, onFacebookClick = onFacebookClick)
    }
}

@Composable
fun AlreadyHaveAccount(
    onClick : () -> Unit,
    isVerification : Boolean
){
    val initialText = if(isVerification) stringResource(id = R.string.not_receive_code)else
        stringResource(R.string.already_have_account) + " "
    val login = if(isVerification) stringResource(id = R.string.resend) else stringResource(id = R.string.login)

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Grey)) {
            pushStringAnnotation(tag = initialText, annotation = initialText)
            append(initialText)
        }
        withStyle(style = SpanStyle(color = BlueAccent, fontWeight = FontWeight.SemiBold)) {
            pushStringAnnotation(tag = login, annotation = login)
            append(login)
        }
    }


    Text(modifier = Modifier
        .padding(top = 36.dp, bottom = 18.dp)
        .fillMaxWidth()
        .clickable { onClick() },
        text = annotatedString,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SocialMediaButton(
    onGoogleClick : () -> Unit,
    onAppleClick : () -> Unit,
    onFacebookClick : () -> Unit
){
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){


        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .size(69.dp)
                .background(White),
            onClick = onGoogleClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = stringResource(R.string.google)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .size(69.dp)
                .background(Color(0xFF4267B2)),
            onClick = onAppleClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.apple),
                contentDescription = stringResource(R.string.apple),
                tint = White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .size(69.dp)
                .background(Soft),
            onClick = onFacebookClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = stringResource(R.string.facebook),
                tint = White
            )
        }
    }
}

fun CoroutineScope.showUnavailableSnackBar(snackHostState: SnackbarHostState) {
    launch {
        snackHostState.showSnackbar(
            message = "This feature is not available",
            actionLabel = "Dismiss",
            duration = SnackbarDuration.Short
        )
    }
}

@Composable
fun OrSignUpButton() {
    Row(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(modifier = Modifier.width(50.dp),color = Soft)

        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = stringResource(id = R.string.or_sign_up_with),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Grey
        )

        HorizontalDivider(modifier = Modifier.width(50.dp),color = Soft)

    }
}

@Preview(showBackground = true)
@Composable
fun AuthPreview(){
    CinemaxTheme {
        AuthScreen(
            modifier =  Modifier,
            onNavigateToLogin = {},
            onNavigateToRegister = {},
            onGoogleClick = {},
            onFacebookClick = {},
            onAppleClick = {}
        )
    }
}