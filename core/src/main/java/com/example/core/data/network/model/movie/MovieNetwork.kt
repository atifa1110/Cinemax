package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class MovieNetwork(
    @SerializedName(Constants.Fields.ADULT)
    val adult: Boolean = false,

    @SerializedName(Constants.Fields.BACKDROP_PATH)
    val backdropPath: String? = null,

    @SerializedName(Constants.Fields.GENRE_IDS)
    val genreIds: List<Int>? = null,

    @SerializedName(Constants.Fields.ID)
    val id: Int? = null,

    @SerializedName(Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String? = null,
    
    @SerializedName(Constants.Fields.ORIGINAL_TITLE)
    val originalTitle: String? = null,

    @SerializedName(Constants.Fields.ORIGINAL_NAME)
    val originalName: String? = null,

    @SerializedName(Constants.Fields.OVERVIEW)
    val overview: String? = null,

    @SerializedName(Constants.Fields.POPULARITY)
    val popularity: Double? = null,

    @SerializedName(Constants.Fields.POSTER_PATH)
    val posterPath: String? = null,

    @SerializedName(Constants.Fields.RELEASE_DATE)
    val releaseDate: String? = null,

    @SerializedName(Constants.Fields.TITLE)
    val title: String? = null,

    @SerializedName(Constants.Fields.VIDEO)
    val video: Boolean = false,

    @SerializedName(Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double? = null,

    @SerializedName(Constants.Fields.VOTE_COUNT)
    val voteCount: Int? = null
)