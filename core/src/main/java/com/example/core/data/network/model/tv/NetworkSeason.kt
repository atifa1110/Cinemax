package com.example.core.data.network.model.tv

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkSeason(
    @SerializedName(Constants.Fields.ID)
    val id: Int,

    @SerializedName(Constants.Fields.NAME)
    val name: String,

    @SerializedName(Constants.Fields.OVERVIEW)
    val overview: String,

    @SerializedName(Constants.Fields.AIR_DATE)
    val airDate: String?,

    @SerializedName(Constants.Fields.EPISODE_COUNT)
    val episodeCount: Int,

    @SerializedName(Constants.Fields.SEASON_NUMBER)
    val seasonNumber: Int,

    @SerializedName(Constants.Fields.POSTER_PATH)
    val posterPath: String?,

    @SerializedName(Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double
)