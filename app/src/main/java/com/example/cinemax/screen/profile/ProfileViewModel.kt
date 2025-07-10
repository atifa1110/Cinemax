package com.example.cinemax.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GetUserId
import com.example.core.domain.usecase.LogoutUseCase
import com.example.core.domain.usecase.SaveLoginStateUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val userId: FirebaseUser? = null,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserId: GetUserId,
    private val logoutUseCase: LogoutUseCase,
    private val saveLoginStateUseCase: SaveLoginStateUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow(ProfileUiState(userId = getUserId()))

    val uiState = _uiState.asStateFlow()

    fun logout(){
        logoutUseCase.invoke()
        setOnLoginState()
    }

    private fun setOnLoginState() = viewModelScope.launch {
            saveLoginStateUseCase(false)
    }

}