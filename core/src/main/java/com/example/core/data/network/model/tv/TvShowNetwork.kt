package com.example.core.data.network.model.tv

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class TvShowNetwork (
    @SerializedName(Constants.Fields.ID)
    val id: Int,

    @SerializedName(Constants.Fields.NAME)
    val name: String? = null,

    @SerializedName(Constants.Fields.OVERVIEW)
    val overview: String? = null,

    @SerializedName(Constants.Fields.POPULARITY)
    val popularity: Double? = null,

    @SerializedName(Constants.Fields.FIRST_AIR_DATE)
    val firstAirDate: String? = null,

    @SerializedName(Constants.Fields.GENRE_IDS)
    val genreIds: List<Int>? = null,

    @SerializedName(Constants.Fields.ORIGINAL_NAME)
    val originalName: String? = null,

    @SerializedName(Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String? = null,

    @SerializedName(Constants.Fields.ORIGIN_COUNTRY)
    val originCountry: List<String>? = null,

    @SerializedName(Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double? = null,

    @SerializedName(Constants.Fields.VOTE_COUNT)
    val voteCount: Int? = null,

    @SerializedName(Constants.Fields.POSTER_PATH)
    val posterPath: String? = null,

    @SerializedName(Constants.Fields.BACKDROP_PATH)
    val backdropPath: String? = null
)