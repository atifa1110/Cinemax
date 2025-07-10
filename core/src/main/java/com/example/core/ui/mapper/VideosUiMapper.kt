package com.example.core.ui.mapper

import com.example.core.domain.model.VideoModel
import com.example.core.domain.model.VideosListModel
import com.example.core.ui.model.Video
import com.example.core.ui.model.VideosList

fun VideosListModel.asVideos() = VideosList(
    results = results.map(VideoModel::asVideos)
)

fun VideoModel.asVideos() = Video(
    id = id,
    country = country,
    language = language,
    key = key,
    name = name,
    official = official,
    publishedAt = publishedAt,
    site = site,
    size = size,
    type = type
)