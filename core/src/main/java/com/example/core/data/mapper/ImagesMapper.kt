package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.ImageEntity
import com.example.core.data.local.model.detailMovie.ImagesListEntity
import com.example.core.data.network.asImageURL
import com.example.core.data.network.model.movie.NetworkImage
import com.example.core.data.network.model.movie.NetworkListImages
import com.example.core.domain.model.ImageModel
import com.example.core.domain.model.ImagesListModel

fun NetworkListImages.asImagesEntity() = ImagesListEntity(
    backdrops = backdrops.map(NetworkImage::asImageEntity),
    posters = posters.map(NetworkImage::asImageEntity)
)

internal fun NetworkImage.asImageEntity() = ImageEntity(
    aspectRatio = aspectRatio,
    height = height,
    width = width,
    filePath = filePath.asImageURL(),
)

fun ImagesListEntity.asImagesModel() = ImagesListModel(
    backdrops = backdrops.map(ImageEntity::asImageModel),
    posters = posters.map(ImageEntity::asImageModel)
)

internal fun ImageEntity.asImageModel() = ImageModel(
    aspectRatio = aspectRatio,
    height = height,
    width = width,
    filePath = filePath,
)