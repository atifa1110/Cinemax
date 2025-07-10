package com.example.cinemax.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinemax.R
import com.example.cinemax.component.AuthCinemaxButton
import com.example.core.ui.components.AuthTopAppBar
import com.example.cinemax.component.CinemaxEmailField
import com.example.cinemax.component.CinemaxPasswordField
import com.example.cinemax.component.CinemaxTextField
import com.example.cinemax.component.LoaderScreen
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.White
import com.example.cinemax.ui.theme.WhiteGrey

@Composable
fun RegisterRoute(
    onNavigateToLogin : () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isRegister) {
        if (uiState.isRegister) {
            onNavigateToLogin()
        }
    }

    RegisterScreen(
        uiState = uiState,
        onNameChange = { name -> viewModel.onNameChange(name)},
        onEmailChange = { email -> viewModel.onEmailChange(email)},
        onPasswordChange = { pass -> viewModel.onPasswordChange(pass)},
        onRegisterClick = { viewModel.registerEmailAndPassword()},
        snackBarMessageShown = { viewModel.snackBarMessageShown() },
        snackBarHostState = snackBarHostState,
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    snackBarMessageShown : () -> Unit,
    loadingContent: @Composable () -> Unit = {
        LoaderScreen(modifier = Modifier.fillMaxSize().background(Dark))
    },
    snackBarHostState: SnackbarHostState,
) {
    // Handle registration success and navigate to the login screen
    if(uiState.isLoading){
        loadingContent()
    }else {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            topBar = {
                AuthTopAppBar(title = R.string.sign_up, isAuthScreen = true, onBackButton = {})
            }
        ) {
            RegisterContent(
                name = uiState.name,
                nameError = uiState.nameError,
                email = uiState.email,
                emailError = uiState.emailError,
                password = uiState.password,
                passwordError = uiState.passwordError,
                success = uiState.isRegister,
                onNameChange = onNameChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onRegisterClick = onRegisterClick,
                modifier = Modifier.padding(it)
            )
        }
    }

    uiState.userMessage?.let { userMessage ->
        LaunchedEffect(userMessage) {
            snackBarHostState.showSnackbar(userMessage)
            snackBarMessageShown()
        }
    }
}

@Composable
fun RegisterContent(
    name: String,
    nameError : String?,
    email : String,
    emailError : String?,
    password : String,
    passwordError : String?,
    success : Boolean,
    onNameChange :  (String) -> Unit,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,
    onRegisterClick : () -> Unit,
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
            text = stringResource(R.string.lets_get_started),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.the_latest_movies_and_series_are_here),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = WhiteGrey
        )

        Spacer(modifier = Modifier.height(40.dp))

        CinemaxTextField(
            text = name,
            textError = nameError,
            labelName = R.string.full_name,
            onTextChange = onNameChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        CinemaxEmailField(
            text = email,
            textError = emailError,
            labelName = R.string.email_address,
            onTextChange = onEmailChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        CinemaxPasswordField(
            password = password,
            passwordError = passwordError,
            labelName = R.string.password,
            onPasswordChange = onPasswordChange
        )

        Spacer(modifier = Modifier.height(8.dp))

        TermAndServiceText(
            checked = false,
            onCheckedChange = {}
        )

        Spacer(modifier = Modifier.height(32.dp))

        AuthCinemaxButton(
            title = R.string.sign_up,
            onButtonClick = onRegisterClick,
            email = email,
            password = password,
        )

    }
}
@Composable
fun TermAndServiceText(
    checked : Boolean,
    onCheckedChange : (Boolean) -> Unit
){
    val initialText = stringResource(R.string.i_agree_to_the)
    val terms = stringResource(R.string.terms_and_services)
    val and = stringResource(R.string.and)
    val privacy = stringResource(R.string.privacy_policy)

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Grey)) {
            pushStringAnnotation(tag = initialText, annotation = initialText)
            append(initialText)
        }
        withStyle(style = SpanStyle(color = BlueAccent, fontWeight = FontWeight.SemiBold)) {
            pushStringAnnotation(tag = terms, annotation = terms)
            append(terms)
        }

        withStyle(style = SpanStyle(color = Grey)) {
            pushStringAnnotation(tag = and, annotation = and)
            append(and)
        }

        withStyle(style = SpanStyle(color = BlueAccent)) {
            pushStringAnnotation(tag = privacy, annotation = privacy)
            append(privacy)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(text = annotatedString, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    val snackBarHostState = remember { SnackbarHostState() }
    CinemaxTheme {
            RegisterScreen(
                uiState = RegisterUiState(
                    name = "Atifa Fiorenza",
                    nameError = null,
                    email = "atifafiorenza24@gmail.com",
                    emailError = null,
                    password = "12345678",
                    passwordError = null,
                    isLoading = false,
                    isRegister = false,
                    userMessage = null
                ),
                onNameChange = {},
                onEmailChange = {},
                onPasswordChange = {},
                onRegisterClick = {},
                snackBarMessageShown = {},
                snackBarHostState = snackBarHostState
            )
    }
}