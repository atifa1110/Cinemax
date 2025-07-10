package com.example.cinemax.screen.search

sealed interface SearchEvent {
    data class ChangeQuery(val value: String) : SearchEvent
    object Refresh : SearchEvent
    object Retry : SearchEvent
    object ClearError : SearchEvent
    data class ShowSnackbar(val message: String) : SearchEvent
}