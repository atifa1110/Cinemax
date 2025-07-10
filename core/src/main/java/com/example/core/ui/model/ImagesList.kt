package com.example.core.ui.model

data class ImagesList(
    val backdrops: List<Image>,
    val posters : List<Image>
)

data class Image(
    val aspectRatio : Double,
    val height : Int,
    val width : Int,
    val filePath : String
)
