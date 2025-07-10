package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkListVideos(
    @SerializedName(Constants.Fields.RESULTS)
    val results : List<NetworkVideo>
)
