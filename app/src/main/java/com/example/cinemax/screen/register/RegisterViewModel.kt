package com.example.cinemax.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.usecase.RegisterWithEmailAndPasswordUseCase
import com.example.core.ui.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class RegisterUiState(
    val name : String = "",
    val nameError : String? = null,
    val email : String = "",
    val emailError : String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val isLoading : Boolean = false,
    val isRegister : Boolean = false,
    val userMessage : String? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterWithEmailAndPasswordUseCase
) :ViewModel(){

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onNameChange(newName : String){
        val error = when {
            newName.isBlank() -> "Name cannot be empty"
            else -> null
        }
        _uiState.update {
            it.copy(name = newName, nameError = error)
        }
    }

    fun onEmailChange(newEmail: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val error = when {
            newEmail.isBlank() -> "Email cannot be empty"
            !newEmail.matches(emailRegex.toRegex()) -> "Invalid email format"
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

    fun registerEmailAndPassword() {
        if(uiState.value.emailError == null &&
            uiState.value.passwordError == null && uiState.value.nameError == null) {
            registerEmailAndPasswordNotEmpty()
        }
    }

    fun snackBarMessageShown(){
        _uiState.value = _uiState.value.copy(userMessage = null)
    }

    private fun registerEmailAndPasswordNotEmpty() {
        viewModelScope.launch {
            val user = User(uiState.value.name,uiState.value.email)
            registerUseCase.invoke(uiState.value.email, uiState.value.password,user).collect { response ->
                when (response) {
                    is CinemaxResponse.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true, isRegister = false)
                        }
                    }

                    is CinemaxResponse.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                userMessage = response.value,
                                isRegister = true
                            )
                        }
                    }

                    is CinemaxResponse.Failure -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isRegister = false,
                                userMessage = response.error
                            )
                        }
                    }
                }
            }
        }
    }

}