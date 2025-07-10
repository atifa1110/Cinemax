package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkListImages(
    @SerializedName(Constants.Fields.BACKDROPS)
    val backdrops: List<NetworkImage>,
    @SerializedName(Constants.Fields.POSTERS)
    val posters : List<NetworkImage>
)
