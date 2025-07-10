package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkBelongsToCollection(
    @SerializedName(Constants.Fields.ID)
    val id: Int,

    @SerializedName(Constants.Fields.NAME)
    val name: String,

    @SerializedName(Constants.Fields.POSTER_PATH)
    val posterPath: String?,

    @SerializedName(Constants.Fields.BACKDROP_PATH)
    val backdropPath: String?
)
