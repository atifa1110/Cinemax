package com.example.core.data.network.model.tv

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkEpisode(
    @SerializedName(Constants.Fields.ID)
    val id: Int?,

    @SerializedName(Constants.Fields.NAME)
    val name: String?,

    @SerializedName(Constants.Fields.AIR_DATE)
    val airDate: String?,

    @SerializedName(Constants.Fields.EPISODE_NUMBER)
    val episodeNumber: Int?,

    @SerializedName(Constants.Fields.OVERVIEW)
    val overview: String?,

    @SerializedName(Constants.Fields.PRODUCTION_CODE)
    val productionCode: String?,

    @SerializedName(Constants.Fields.RUNTIME)
    val runtime: Int?,

    @SerializedName(Constants.Fields.SEASON_NUMBER)
    val seasonNumber: Int?,

    @SerializedName(Constants.Fields.SHOW_ID)
    val showId: Int?,

    @SerializedName(Constants.Fields.STILL_PATH)
    val stillPath: String?,

    @SerializedName(Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double?,

    @SerializedName(Constants.Fields.VOTE_COUNT)
    val voteCount: Int?
)
