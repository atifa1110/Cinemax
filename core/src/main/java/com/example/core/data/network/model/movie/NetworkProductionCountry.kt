package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkProductionCountry(
    @SerializedName(Constants.Fields.NAME)
    val name: String,

    @SerializedName(Constants.Fields.ISO31661)
    val iso: String
)