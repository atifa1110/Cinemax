package com.example.core.ui.mapper
import com.example.core.domain.model.VideoModel
import com.example.core.domain.model.VideosListModel
import org.junit.Assert.assertEquals
import org.junit.Test

class VideosUiMapperTest {

    @Test
    fun `asVideos maps VideosListModel to VideosList correctly`() {
        val videoModel = VideoModel(
            id = "abc123",
            country = "US",
            language = "en",
            key = "someKey",
            name = "Trailer",
            official = true,
            publishedAt = "2023-01-01T00:00:00Z",
            site = "YouTube",
            size = 1080,
            type = "Trailer"
        )

        val model = VideosListModel(results = listOf(videoModel))

        val result = model.asVideos()

        val video = result.results.first()
        assertEquals(videoModel.id, video.id)
        assertEquals(videoModel.country, video.country)
        assertEquals(videoModel.language, video.language)
        assertEquals(videoModel.key, video.key)
        assertEquals(videoModel.name, video.name)
        assertEquals(videoModel.official, video.official)
        assertEquals(videoModel.publishedAt, video.publishedAt)
        assertEquals(videoModel.site, video.site)
        assertEquals(videoModel.size, video.size)
        assertEquals(videoModel.type, video.type)
    }
}
