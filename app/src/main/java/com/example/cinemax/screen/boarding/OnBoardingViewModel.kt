package com.example.cinemax.screen.boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.SaveBoardingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveBoardingStateUseCase: SaveBoardingStateUseCase
) : ViewModel(){

    fun setOnBoardingState (completed : Boolean){
        viewModelScope.launch {
            saveBoardingStateUseCase(completed)
        }
    }
}