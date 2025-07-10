package com.example.core.ui.mapper

interface EventHandler<E> {
    fun onEvent(event: E)
}