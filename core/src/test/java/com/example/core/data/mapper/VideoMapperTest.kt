package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.VideoEntity
import com.example.core.data.local.model.detailMovie.VideosListEntity
import com.example.core.data.network.model.movie.NetworkListVideos
import com.example.core.data.network.model.movie.NetworkVideo
import com.example.core.domain.model.VideoModel
import com.example.core.domain.model.VideosListModel
import kotlin.test.Test
import kotlin.test.assertEquals

class VideoMapperTest {

    private val networkVideo = NetworkVideo(
        id = "123",
        language = "en",
        country = "US",
        key = "abc123",
        name = "Official Trailer",
        official = true,
        publishedAt = "2023-05-01T00:00:00Z",
        site = "YouTube",
        size = 1080,
        type = "Trailer"
    )

    private val videoEntity = VideoEntity(
        id = "123",
        language = "en",
        country = "US",
        key = "abc123",
        name = "Official Trailer",
        official = true,
        publishedAt = "2023-05-01T00:00:00Z",
        site = "YouTube",
        size = 1080,
        type = "Trailer"
    )

    private val videoModel = VideoModel(
        id = "123",
        language = "en",
        country = "US",
        key = "abc123",
        name = "Official Trailer",
        official = true,
        publishedAt = "2023-05-01T00:00:00Z",
        site = "YouTube",
        size = 1080,
        type = "Trailer"
    )

    @Test
    fun `NetworkListVideos is mapped to VideosListEntity correctly`() {
        val networkList = NetworkListVideos(results = listOf(networkVideo))
        val expected = VideosListEntity(results = listOf(videoEntity))
        val result = networkList.asVideoEntity()
        assertEquals(expected, result)
    }

    @Test
    fun `VideosListEntity is mapped to VideosListModel correctly`() {
        val entity = VideosListEntity(results = listOf(videoEntity))
        val expected = VideosListModel(results = listOf(videoModel))
        val result = entity.asVideoModel()
        assertEquals(expected, result)
    }
}
