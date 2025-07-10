package com.example.core.ui.model

data class VideosList(
    val results : List<Video>
)

data class Video(
    val id: String,
    val language: String,
    val country: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val publishedAt: String
)