package com.example.cinemax.screen.home

sealed interface HomeEvent {
    object Refresh : HomeEvent
    object Retry : HomeEvent
    object ClearError : HomeEvent
}