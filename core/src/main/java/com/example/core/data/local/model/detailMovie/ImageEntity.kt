package com.example.core.data.local.model.detailMovie

import com.example.core.data.local.util.Constants
import com.google.gson.annotations.SerializedName

data class ImageEntity (
    @SerializedName(Constants.Fields.ASPECT_RATIO)
    val aspectRatio : Double,
    @SerializedName(Constants.Fields.HEIGHT)
    val height : Int,
    @SerializedName(Constants.Fields.WIDTH)
    val width : Int,
    @SerializedName(Constants.Fields.FILE_PATH)
    val filePath : String
)