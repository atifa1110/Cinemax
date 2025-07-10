package com.example.cinemax.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinemax.R
import com.example.cinemax.component.AuthCinemaxButton
import com.example.core.ui.components.AuthTopAppBar
import com.example.cinemax.component.CinemaxPasswordField
import com.example.cinemax.component.CinemaxTextField
import com.example.cinemax.component.LoaderScreen
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.White
import com.example.cinemax.ui.theme.WhiteGrey
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginRoute(
    onNavigateToForgotPassword : () -> Unit,
    onNavigateToHome : () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
){
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState.isLogin) {
        if (uiState.isLogin) {
            onNavigateToHome()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onEmailChange = { viewModel.onEmailChange(it)},
        onPasswordChange = { viewModel.onPasswordChange(it)},
        onLoginClick = {
            keyboardController?.hide()
            viewModel.signInEmailAndPassword()
        },
        onForgotPasswordClick = onNavigateToForgotPassword,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick : () -> Unit,
    onForgotPasswordClick : () -> Unit,
    loadingContent: @Composable () -> Unit = {
        LoaderScreen(modifier = Modifier.fillMaxSize())
    },
    snackBarHostState: SnackbarHostState
) {
    if (uiState.isLoading) {
        loadingContent()
    } else {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            topBar = {
                AuthTopAppBar(title = R.string.login, isAuthScreen = true, onBackButton = {})
            }
        ) {
            LoginContent(
                email = uiState.email,
                emailError = uiState.emailError,
                password = uiState.password,
                passwordError = uiState.passwordError,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onLoginClick = onLoginClick,
                onForgotPasswordClick = onForgotPasswordClick,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
fun LoginContent(
    email: String,
    emailError : String?,
    password : String,
    passwordError: String?,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick : () -> Unit,
    modifier : Modifier = Modifier
){
    Column(
        modifier
            .fillMaxSize()
            .background(Dark)
            .padding(vertical = 16.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.hi_tiffany),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.welcome_back_please_enter_your_details),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = WhiteGrey
        )

        Spacer(modifier = Modifier.height(40.dp))

        CinemaxTextField(
            text = email,
            textError = emailError,
            labelName = R.string.email_address,
            onTextChange = onEmailChange)

        Spacer(modifier = Modifier.height(16.dp))

        CinemaxPasswordField(
            password = password,
            passwordError = passwordError,
            labelName = R.string.password,
            onPasswordChange = onPasswordChange
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.forgot_password),
            color = BlueAccent,
            modifier = Modifier.testTag("ForgotButton")
                .align(Alignment.End)
                .clickable { onForgotPasswordClick() }
        )
        Spacer(modifier = Modifier.height(32.dp))

        AuthCinemaxButton(
            title = R.string.login,
            onButtonClick = onLoginClick,
            email = email,
            password = password,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    CinemaxTheme {
        val snackBarHostState = remember { SnackbarHostState() }

            LoginScreen(
                uiState = LoginUiState(
                    email = "atifafiorenza24@gmail.com",
                    emailError = null,
                    password = "12345678",
                    passwordError = null,
                    isLoading = false,
                    isLogin = false,
                ),
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClick = {},
                onForgotPasswordClick = {},
                snackBarHostState = snackBarHostState
            )
    }
}