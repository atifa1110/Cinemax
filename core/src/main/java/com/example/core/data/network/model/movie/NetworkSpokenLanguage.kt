package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkSpokenLanguage(
    @SerializedName(Constants.Fields.NAME)
    val name: String,

    @SerializedName(Constants.Fields.ENGLISH_NAME)
    val englishName: String,

    @SerializedName(Constants.Fields.ISO6391)
    val iso: String
)