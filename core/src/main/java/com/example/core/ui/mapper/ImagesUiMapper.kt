package com.example.core.ui.mapper

import com.example.core.domain.model.ImageModel
import com.example.core.domain.model.ImagesListModel
import com.example.core.ui.model.Image
import com.example.core.ui.model.ImagesList

internal fun ImagesListModel.asImages() = ImagesList(
    backdrops = backdrops.map(ImageModel::asImage),
    posters = posters.map(ImageModel::asImage)
)

private fun ImageModel.asImage() = Image(
    aspectRatio = aspectRatio,
    height = height,
    width = width,
    filePath = filePath
)