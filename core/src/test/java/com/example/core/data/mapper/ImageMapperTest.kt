package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.ImagesListEntity
import com.example.core.data.network.model.movie.NetworkImage
import com.example.core.data.network.model.movie.NetworkListImages
import com.example.core.data.network.asImageURL
import kotlin.test.Test
import kotlin.test.assertEquals

class ImageMapperTest {

    private val sampleNetworkImage = NetworkImage(
        aspectRatio = 1.78,
        height = 1080,
        width = 1920,
        filePath = "/image.jpg"
    )

    @Test
    fun `NetworkListImages is mapped correctly to ImagesListEntity`() {
        val network = NetworkListImages(
            backdrops = listOf(sampleNetworkImage),
            posters = listOf(sampleNetworkImage)
        )

        val entity = network.asImagesEntity()

        val backdrop = entity.backdrops[0]
        val poster = entity.posters[0]

        assertEquals(1.78, backdrop.aspectRatio)
        assertEquals(1080, backdrop.height)
        assertEquals("/image.jpg".asImageURL(), backdrop.filePath)

        assertEquals(1920, poster.width)
        assertEquals("/image.jpg".asImageURL(), poster.filePath)
    }

    @Test
    fun `ImagesListEntity is mapped correctly to ImagesListModel`() {
        val entity = ImagesListEntity(
            backdrops = listOf(sampleNetworkImage.asImageEntity()),
            posters = listOf(sampleNetworkImage.asImageEntity())
        )

        val model = entity.asImagesModel()

        val backdropModel = model.backdrops[0]
        val posterModel = model.posters[0]

        assertEquals(1.78, backdropModel.aspectRatio)
        assertEquals(1080, posterModel.height)
        assertEquals("/image.jpg".asImageURL(), posterModel.filePath)
    }

    @Test
    fun `Empty NetworkListImages maps to empty ImagesListEntity`() {
        val empty = NetworkListImages(emptyList(), emptyList()).asImagesEntity()
        assertEquals(0, empty.backdrops.size)
        assertEquals(0, empty.posters.size)
    }
}
