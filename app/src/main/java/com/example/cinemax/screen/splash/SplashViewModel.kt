package com.example.cinemax.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GetBoardStateUseCase
import com.example.core.domain.usecase.GetLoginStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoginStateUseCase: GetLoginStateUseCase,
    private val getBoardStateUseCase: GetBoardStateUseCase
) : ViewModel(){

    val onBoardingState = getBoardStateUseCase().stateIn(viewModelScope, SharingStarted.Eagerly, false)
    val onLoginState = getLoginStateUseCase().stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _isLoadingComplete = MutableStateFlow(false)
    val isLoadingComplete: StateFlow<Boolean> = _isLoadingComplete.asStateFlow()

    init {
        viewModelScope.launch {
            delay(100L)
            _isLoadingComplete.value = true
        }
    }


}
