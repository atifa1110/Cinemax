package com.example.cinemax

import com.example.cinemax.screen.login.LoginEvent
import com.example.cinemax.screen.login.LoginUiState
import com.example.cinemax.screen.login.LoginViewModelContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FakeLoginViewModel : LoginViewModelContract {
    private val _uiState = MutableStateFlow(LoginUiState())
    override val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LoginEvent>()
    override val eventFlow = _eventFlow.asSharedFlow()

    override fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    override fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    override fun signInEmailAndPassword() {
        // langsung emit error
        CoroutineScope(Dispatchers.Main).launch {
            _eventFlow.emit(LoginEvent.ShowSnackbar("Authentication Success"))
        }
    }

    fun signInEmailAndPasswordError() {
        // langsung emit error
        CoroutineScope(Dispatchers.Main).launch {
            _eventFlow.emit(LoginEvent.ShowSnackbar("Authentication Failed, Check email and password"))
        }
    }
}
