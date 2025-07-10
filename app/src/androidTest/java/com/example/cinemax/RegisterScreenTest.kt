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
import com.example.cinemax.screen.register.RegisterScreen
import com.example.cinemax.screen.register.RegisterUiState
import com.example.core.ui.theme.CinemaxTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterNameEmailPasswordShowsCorrectInput() {
        composeTestRule.setContent {
            CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(RegisterUiState()) }

                RegisterScreen(
                    uiState = uiState.value,
                    onNameChange = { newName ->
                        uiState.value = uiState.value.copy(
                            name = newName,
                            nameError = if(newName.isBlank()) "Name cannot be empty" else null
                        )
                    },
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
                                "Invalid email format"
                            else if(newEmail.isBlank()) "Email cannot be empty" else null
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
                    onRegisterClick = {},
                    snackBarMessageShown = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Verify name and error message appears
        composeTestRule.onNodeWithTag("TextInput").performTextInput("Atifa Fiorenza")
        composeTestRule.onNodeWithTag("TextError").assertDoesNotExist()
        // Verify email and error message appears
        composeTestRule.onNodeWithTag("EmailInput").performTextInput("atifafiorenza24@gmail.com")
        composeTestRule.onNodeWithTag("EmailError").assertDoesNotExist()
        // Verify password and error message appears
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput("12345678")
        composeTestRule.onNodeWithTag("PasswordError").assertDoesNotExist()
    }

    @Test
    fun enterNameEmailPasswordShowsErrorInput() {
        composeTestRule.setContent {
           CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(RegisterUiState()) }

                RegisterScreen(
                    uiState = uiState.value,
                    onNameChange = { newName ->
                        uiState.value = uiState.value.copy(
                            name = newName,
                            nameError = if(newName.isBlank()) "Name cannot be empty" else null
                        )
                    },
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
                                "Invalid email format"
                            else if(newEmail.isBlank()) "Email cannot be empty" else null
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
                    onRegisterClick = {},
                    snackBarMessageShown = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Verify name and error message appears
        composeTestRule.onNodeWithTag("TextInput").performTextInput(" ")
        composeTestRule.onNodeWithTag("TextError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("TextError").assertTextContains("* Name cannot be empty", substring = true)
        // Verify email and error message appears
        composeTestRule.onNodeWithTag("EmailInput").performTextInput("atifafiorenza24")
        composeTestRule.onNodeWithTag("EmailError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("EmailError").assertTextContains("* Invalid email format", substring = true)
        // Verify password and error message appears
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput("1234")
        composeTestRule.onNodeWithTag("PasswordError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordError").assertTextContains("* Password must be at least 8 characters", substring = true)
    }

    @Test
    fun enterNameEmailPasswordShowsEmptyInput() {
        composeTestRule.setContent {
            CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(RegisterUiState()) }

                RegisterScreen(
                    uiState = uiState.value,
                    onNameChange = { newName ->
                        uiState.value = uiState.value.copy(
                            name = newName,
                            nameError = if(newName.isBlank()) "Name cannot be empty" else null
                        )
                    },
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if(newEmail.isBlank()) "Email cannot be empty" else null
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
                    onRegisterClick = {},
                    snackBarMessageShown = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Verify name and error message appears
        composeTestRule.onNodeWithTag("TextInput").performTextInput(" ")
        composeTestRule.onNodeWithTag("TextError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("TextError").assertTextContains("* Name cannot be empty", substring = true)
        // Verify email and error message appears
        composeTestRule.onNodeWithTag("EmailInput").performTextInput(" ")
        composeTestRule.onNodeWithTag("EmailError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("EmailError").assertTextContains("* Email cannot be empty", substring = true)
        // Verify password and error message appears
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput(" ")
        composeTestRule.onNodeWithTag("PasswordError").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordError").assertTextContains("* Password cannot be empty", substring = true)
    }

    @Test
    fun enterEmailAndPasswordShowsRegisterSuccess() {
        var navigateToLogin = false

        composeTestRule.setContent {
            CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(RegisterUiState()) }
                RegisterScreen(
                    uiState = uiState.value,
                    onNameChange = { newName ->
                        uiState.value = uiState.value.copy(
                            name = newName,
                            nameError = if(newName.isBlank()) "Name cannot be empty" else null
                        )
                    },
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
                                "Invalid email format"
                            else if(newEmail.isBlank()) "Email cannot be empty" else null
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
                    onRegisterClick = {
                        uiState.value = uiState.value.copy(
                            isRegister = true
                        )
                    },
                    snackBarMessageShown = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Enter text into fields
        composeTestRule.onNodeWithTag("TextInput").performTextInput("Atifa Fiorenza")
        composeTestRule.onNodeWithTag("EmailInput").performTextInput("atifafiorenza24@gmail.com")
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput("12345678")

        // Click Register Button
        composeTestRule.onNodeWithTag("AuthButton").assertExists().performClick()

        // Ensures UI updates before assert
        composeTestRule.waitForIdle()

        // Verify navigate is success
        assert(navigateToLogin)
    }

    @Test
    fun enterEmailAndPasswordShowsRegisterError() {
        composeTestRule.setContent {
            CinemaxTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val uiState = remember { mutableStateOf(RegisterUiState()) }
                RegisterScreen(
                    uiState = uiState.value,
                    onNameChange = { newName ->
                        uiState.value = uiState.value.copy(
                            name = newName,
                            nameError = if(newName.isBlank()) "Name cannot be empty" else null
                        )
                    },
                    onEmailChange = { newEmail ->
                        uiState.value = uiState.value.copy(
                            email = newEmail,
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
                                "Invalid email format"
                            else if(newEmail.isBlank()) "Email cannot be empty" else null
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
                    onRegisterClick = {
                        uiState.value = uiState.value.copy(
                            isRegister = false,
                            userMessage = "Authentication failed, Email already registered."
                        )
                    },
                    snackBarMessageShown = {},
                    snackBarHostState = snackBarHostState
                )
            }
        }

        // Enter text into fields
        composeTestRule.onNodeWithTag("TextInput").performTextInput("Atifa Fiorenza")
        composeTestRule.onNodeWithTag("EmailInput").performTextInput("atifafiorenza24@gmail.com")
        composeTestRule.onNodeWithTag("PasswordInput").performTextInput("1234567890")

        // Click Register Button
        composeTestRule.onNodeWithTag("AuthButton").assertExists().performClick()

        // Ensures UI updates before snack-bar appear
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Authentication failed, Email already registered.")
            .assertIsDisplayed()
    }


}