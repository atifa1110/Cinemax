package com.example.core.data.network.response

import com.example.core.data.network.Constants
import com.example.core.data.network.model.genre.GenreNetwork
import com.google.gson.annotations.SerializedName

data class GenreResponse (
    @SerializedName(Constants.Fields.GENRES)
    val genres: List<GenreNetwork>?= emptyList(),
)