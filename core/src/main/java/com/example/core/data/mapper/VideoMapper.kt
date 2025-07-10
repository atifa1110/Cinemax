package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.VideoEntity
import com.example.core.data.local.model.detailMovie.VideosListEntity
import com.example.core.data.network.model.movie.NetworkListVideos
import com.example.core.data.network.model.movie.NetworkVideo
import com.example.core.domain.model.VideoModel
import com.example.core.domain.model.VideosListModel

fun NetworkListVideos.asVideoEntity() = VideosListEntity(
    results = results.map(NetworkVideo::asVideoEntity)
)

private fun NetworkVideo.asVideoEntity() = VideoEntity(
    id = id,
    language = language,
    country = country,
    key = key,
    name = name,
    official = official,
    publishedAt = publishedAt,
    site = site,
    size = size,
    type = type
)

fun VideosListEntity.asVideoModel() = VideosListModel(
    results = results.map(VideoEntity::asVideoModel)
)

private fun VideoEntity.asVideoModel() = VideoModel(
    id = id,
    language = language,
    country = country,
    key = key,
    name = name,
    official = official,
    publishedAt = publishedAt,
    site = site,
    size = size,
    type = type
)