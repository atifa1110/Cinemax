package com.example.core.data.network.model.movie


import com.example.core.data.network.Constants
import com.example.core.data.network.model.genre.GenreNetwork
import com.google.gson.annotations.SerializedName

data class MovieDetailNetwork(
    @SerializedName(Constants.Fields.ID)
    val id: Int,

    @SerializedName(Constants.Fields.ADULT)
    val adult: Boolean,

    @SerializedName(Constants.Fields.BACKDROP_PATH)
    val backdropPath: String? = null,

    @SerializedName(Constants.Fields.BELONGS_TO_COLLECTION)
    val belongsToCollection: NetworkBelongsToCollection?= null,

    @SerializedName(Constants.Fields.BUDGET)
    val budget: Int? = null,

    @SerializedName(Constants.Fields.GENRES)
    val genres: List<GenreNetwork>? = null,

    @SerializedName(Constants.Fields.HOMEPAGE)
    val homepage: String? = null,

    @SerializedName(Constants.Fields.IMDB_ID)
    val imdbId: String? = null,

    @SerializedName(Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String? = null,

    @SerializedName(Constants.Fields.ORIGINAL_TITLE)
    val originalTitle: String? = null,

    @SerializedName(Constants.Fields.OVERVIEW)
    val overview: String? = null,

    @SerializedName(Constants.Fields.POPULARITY)
    val popularity: Double?= null,

    @SerializedName(Constants.Fields.POSTER_PATH)
    val posterPath: String? = null,

    @SerializedName(Constants.Fields.PRODUCTION_COMPANIES)
    val productionCompanies: List<NetworkProductionCompany>? = null,

    @SerializedName(Constants.Fields.PRODUCTION_COUNTRIES)
    val productionCountries: List<NetworkProductionCountry>? = null,

    @SerializedName(Constants.Fields.RELEASE_DATE)
    val releaseDate: String? = null,

    @SerializedName(Constants.Fields.REVENUE)
    val revenue: Long?= null,

    @SerializedName(Constants.Fields.RUNTIME)
    val runtime: Int?= null,

    @SerializedName(Constants.Fields.SPOKEN_LANGUAGES)
    val spokenLanguages: List<NetworkSpokenLanguage>? = null,

    @SerializedName(Constants.Fields.STATUS)
    val status: String? = null,

    @SerializedName(Constants.Fields.TAGLINE)
    val tagline: String?= null,

    @SerializedName(Constants.Fields.TITLE)
    val title: String?= null,

    @SerializedName(Constants.Fields.VIDEO)
    val video: Boolean?= null,

    @SerializedName(Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double?= null,

    @SerializedName(Constants.Fields.VOTE_COUNT)
    val voteCount: Int?= null,

    @SerializedName(Constants.Fields.CREDITS)
    val credits: NetworkListCredits? = null,

    @SerializedName(Constants.Fields.IMAGES)
    val images: NetworkListImages? = null,

    @SerializedName(Constants.Fields.VIDEOS)
    val videos: NetworkListVideos? = null,
)
