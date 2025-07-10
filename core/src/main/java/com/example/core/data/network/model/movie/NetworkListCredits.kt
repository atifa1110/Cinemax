package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkListCredits(
    @SerializedName(Constants.Fields.CAST)
    val cast: List<NetworkCast>,

    @SerializedName(Constants.Fields.CREW)
    val crew: List<NetworkCrew>
)