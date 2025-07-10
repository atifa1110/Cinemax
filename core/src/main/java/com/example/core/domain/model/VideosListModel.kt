package com.example.core.domain.model

data class VideosListModel (
    val results : List<VideoModel>
)

data class VideoModel(
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