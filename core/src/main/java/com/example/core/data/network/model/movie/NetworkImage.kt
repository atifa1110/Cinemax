package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkImage(
    @SerializedName(Constants.Fields.ASPECT_RATIO)
    val aspectRatio : Double,
    @SerializedName(Constants.Fields.HEIGHT)
    val height : Int,
    @SerializedName(Constants.Fields.WIDTH)
    val width : Int,
    @SerializedName(Constants.Fields.FILE_PATH)
    val filePath : String
)