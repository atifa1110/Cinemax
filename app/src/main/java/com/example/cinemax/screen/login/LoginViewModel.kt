package com.example.cinemax.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemax.screen.login.LoginEvent.ShowSnackbar
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.usecase.SignInWithEmailAndPasswordUseCase
import com.example.core.domain.usecase.SaveLoginStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email : String = "",
    val emailError : String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val isLoading : Boolean = false,
    val isLogin : Boolean = false,
)

sealed class LoginEvent {
    data class ShowSnackbar(val message: String) : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveLoginStateUseCase: SaveLoginStateUseCase,
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LoginEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEmailChange(newEmail: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val error = when {
            newEmail.isBlank() -> "Email cannot be empty"
            !newEmail.matches(emailRegex.toRegex())-> "Invalid email format"
            else -> null
        }
        _uiState.update {
            it.copy(email = newEmail, emailError = error)
        }
    }

    fun onPasswordChange(newPassword: String) {
        val error = when {
            newPassword.isBlank() -> "Password cannot be empty"
            newPassword.length < 8 -> "Password must be at least 8 characters"
            else -> null
        }
        _uiState.update {
            it.copy(password = newPassword, passwordError = error)
        }
    }
    private fun setLoginState(completed: Boolean) = viewModelScope.launch {
        saveLoginStateUseCase(completed)
    }

    fun signInEmailAndPassword() = viewModelScope.launch {
        signInWithEmailAndPasswordUseCase.invoke(uiState.value.email, uiState.value.password)
            .collect { result ->
                when (result) {
                    is CinemaxResponse.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLogin = true,
                            )
                        }
                        _eventFlow.emit(ShowSnackbar(result.value))
                        setLoginState(_uiState.value.isLogin)
                    }

                    is CinemaxResponse.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                isLogin = false
                            )
                        }
                    }

                    is CinemaxResponse.Failure -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLogin = false,
                            )
                        }
                        _eventFlow.emit(ShowSnackbar(result.error))
                    }
                }
            }
    }
}