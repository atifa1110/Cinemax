package com.example.core.ui.mapper

import com.example.core.domain.model.ImageModel
import com.example.core.domain.model.ImagesListModel
import com.example.core.ui.model.Image
import com.example.core.ui.model.ImagesList
import org.junit.Assert.assertEquals
import org.junit.Test


class ImagesUiMapperTest {

    @Test
    fun `asImages should map ImagesListModel to ImagesList correctly`() {
        // Given
        val backdrops = listOf(
            ImageModel(
                aspectRatio = 1.78,
                height = 1080,
                width = 1920,
                filePath = "/backdrop.jpg"
            )
        )
        val posters = listOf(
            ImageModel(
                aspectRatio = 0.67,
                height = 1500,
                width = 1000,
                filePath = "/poster.jpg"
            )
        )

        val imagesListModel = ImagesListModel(
            backdrops = backdrops,
            posters = posters
        )

        // When
        val result = imagesListModel.asImages()

        // Then
        val expected = ImagesList(
            backdrops = listOf(
                Image(
                    aspectRatio = 1.78,
                    height = 1080,
                    width = 1920,
                    filePath = "/backdrop.jpg"
                )
            ),
            posters = listOf(
                Image(
                    aspectRatio = 0.67,
                    height = 1500,
                    width = 1000,
                    filePath = "/poster.jpg"
                )
            )
        )

        assertEquals(expected, result)
    }
}

