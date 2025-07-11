package com.example.cinemax

import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cinemax.screen.login.LoginScreen
import com.example.cinemax.screen.login.LoginUiState
import com.example.cinemax.ui.theme.CinemaxTheme
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    // Test Cases :
    // verify email and password input error message
    // verify email and password input empty error message
    // verify email and password input and login error
    // verify email and password input and login success
    // clicking login button and show loading state
    // clicking forgot password button and navigate to forgot password page

    @Test
    fun enterEmailAndPasswordShowsErrorInput() {
        composeTestRule.setContent {
            CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(LoginUiState()) }

                LoginScreen(
                    uiState = uiState.value,
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
                                "Invalid email format"
                            else null
                        )
                    },
                    onPasswordChange = { newPassword ->
                        uiState.value = uiState.value.copy(
                            password = newPassword,
                            passwordError = if(newPassword.length <8)
                                "Password must be at least 8 characters"
                            else null
                        )
                    },
                    eventFlow = emptyFlow(),
                    onLoginClick = {},
                    onForgotPasswordClick = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Verify email error message appears
        composeTestRule.onNodeWithTag("EmailInput").performTextInput("atifafiorenza24")
        composeTestRule.onNodeWithTag("EmailError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("EmailError").assertTextContains("* Invalid email format", substring = true)

        // Verify password error message appears
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput("123")
        composeTestRule.onNodeWithTag("PasswordError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordError").assertTextContains("* Password must be at least 8 characters", substring = true)
    }

    @Test
    fun enterEmailAndPasswordShowsErrorEmptyInput() {
        composeTestRule.setContent {
           CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(LoginUiState()) }

                LoginScreen(
                    uiState = uiState.value,
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if (newEmail.isBlank())
                                "Email cannot be empty"
                            else null
                        )
                    },
                    onPasswordChange = { newPassword ->
                        uiState.value = uiState.value.copy(
                            password = newPassword,
                            passwordError = if(newPassword.isBlank())
                                "Password cannot be empty"
                            else null
                        )
                    },
                    eventFlow = emptyFlow(),
                    onLoginClick = {},
                    onForgotPasswordClick = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Verify email error message appears
        composeTestRule.onNodeWithTag("EmailInput").performTextInput(" ")
        composeTestRule.onNodeWithTag("EmailError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("EmailError").assertTextContains("* Email cannot be empty", substring = true)

        // Verify password error message appears
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput(" ")
        composeTestRule.onNodeWithTag("PasswordError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordError").assertTextContains("* Password cannot be empty", substring = true)
    }

    @Test
    fun enterEmailAndPasswordShowsLoginError() {
        val view = FakeLoginViewModel()

        composeTestRule.setContent {
            CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(LoginUiState()) }

                LoginScreen(
                    uiState = uiState.value,
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
                                "Invalid email format"
                            else null
                        )
                    },
                    onPasswordChange = { newPassword ->
                        uiState.value = uiState.value.copy(
                            password = newPassword,
                            passwordError = if(newPassword.length < 8)
                                "Password must be at least 8 characters"
                            else null
                        )
                    },
                    eventFlow = view.eventFlow,
                    onLoginClick = {view.signInEmailAndPasswordError()},
                    onForgotPasswordClick = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Verify email error message appears
        composeTestRule.onNodeWithTag("EmailInput").performTextInput("atifafiorenza24@gmail.com")
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput("1234567890")
        composeTestRule.onNodeWithTag("AuthButton").assertExists().performClick()
        // Verify that the snackbar error message appears
        composeTestRule.waitForIdle() // Ensures UI updates before assertion
        composeTestRule.onNodeWithText("Authentication Failed, Check email and password")
            .assertIsDisplayed()
    }

    @Test
    fun enterEmailAndPasswordShowsLoginSuccess() {
        val view = FakeLoginViewModel()
        composeTestRule.setContent {
           CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(LoginUiState()) }

                LoginScreen(
                    uiState = uiState.value,
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
                                "Invalid email format"
                            else null
                        )
                    },
                    onPasswordChange = { newPassword ->
                        uiState.value = uiState.value.copy(
                            password = newPassword,
                            passwordError = if(newPassword.length < 8)
                                "Password must be at least 8 characters"
                            else null
                        )
                    },
                    onLoginClick = {
                        view.signInEmailAndPassword()
                    },
                    eventFlow = view.eventFlow,
                    onForgotPasswordClick = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Verify email error message appears
        composeTestRule.onNodeWithTag("EmailInput").performTextInput("atifafiorenza24@gmail.com")
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput("123456789")
        composeTestRule.onNodeWithTag("AuthButton").assertExists().performClick()

        // Ensures UI updates before assertion
        composeTestRule.waitForIdle() // Ensures UI updates before assertion
        composeTestRule.onNodeWithText("Authentication Success").assertIsDisplayed()
    }

    @Test
    fun clickingLoginButtonShowsLoadingState() {
        composeTestRule.setContent {
            CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(LoginUiState()) }

                LoginScreen(
                    uiState = uiState.value,
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
                                "Invalid email format"
                            else null
                        )
                    },
                    onPasswordChange = { newPassword ->
                        uiState.value = uiState.value.copy(
                            password = newPassword,
                            passwordError = if(newPassword.length < 8)
                                "Password must be at least 8 characters"
                            else null
                        )
                    },
                    onLoginClick = {
                        uiState.value = uiState.value.copy(isLoading = true)
                    },
                    eventFlow = emptyFlow(),
                    onForgotPasswordClick = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Click the login button
        composeTestRule.onNodeWithTag("EmailInput").performTextInput("atifafiorenza24@gmail.com")
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput("123456789")
        composeTestRule.onNodeWithTag("AuthButton").assertExists().performClick()

        // Verify that the loading indicator is displayed
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("LoaderScreen").assertExists()
    }

}