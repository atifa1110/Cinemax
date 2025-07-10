package com.example.core.data.network.model.tv

import com.example.core.data.network.Constants
import com.example.core.data.network.model.genre.GenreNetwork
import com.example.core.data.network.model.movie.NetworkListCredits
import com.example.core.data.network.model.movie.NetworkProductionCompany
import com.example.core.data.network.model.movie.NetworkProductionCountry
import com.example.core.data.network.model.movie.NetworkSpokenLanguage
import com.google.gson.annotations.SerializedName

data class TvShowDetailNetwork (
    @SerializedName(Constants.Fields.ID)
    val id: Int,

    @SerializedName(Constants.Fields.NAME)
    val name: String,

    @SerializedName(Constants.Fields.ADULT)
    val adult: Boolean,

    @SerializedName(Constants.Fields.BACKDROP_PATH)
    val backdropPath: String? = null,

    @SerializedName(Constants.Fields.CREATED_BY)
    val createdBy: List<NetworkCreatedBy>? = null,

    @SerializedName(Constants.Fields.CREDITS)
    val credits: NetworkListCredits? = null,

    @SerializedName(Constants.Fields.EPISODE_RUN_TIME)
    val episodeRunTime: List<Int>? = null,

    @SerializedName(Constants.Fields.FIRST_AIR_DATE)
    val firstAirDate: String? = null,

    @SerializedName(Constants.Fields.GENRES)
    val genres: List<GenreNetwork>? = null,

    @SerializedName(Constants.Fields.HOMEPAGE)
    val homepage: String? = null,

    @SerializedName(Constants.Fields.IN_PRODUCTION)
    val inProduction: Boolean = false,

    @SerializedName(Constants.Fields.LANGUAGES)
    val languages: List<String>? = null,

    @SerializedName(Constants.Fields.LAST_AIR_DATE)
    val lastAirDate: String? = null,

    @SerializedName(Constants.Fields.LAST_EPISODE_TO_AIR)
    val lastEpisodeToAir: NetworkEpisode? = null,

    @SerializedName(Constants.Fields.NETWORKS)
    val organizations: List<NetworkOrganization>? = null,

    @SerializedName(Constants.Fields.NEXT_EPISODE_TO_AIR)
    val nextEpisodeToAir: NetworkEpisode? = null,

    @SerializedName(Constants.Fields.NUMBER_OF_EPISODES)
    val numberOfEpisodes: Int? = null,

    @SerializedName(Constants.Fields.NUMBER_OF_SEASONS)
    val numberOfSeasons: Int? = null,

    @SerializedName(Constants.Fields.ORIGIN_COUNTRY)
    val originCountry: List<String>? = null,

    @SerializedName(Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String? = null,

    @SerializedName(Constants.Fields.ORIGINAL_NAME)
    val originalName: String? = null,

    @SerializedName(Constants.Fields.OVERVIEW)
    val overview: String? = null,

    @SerializedName(Constants.Fields.POPULARITY)
    val popularity: Double? = null,

    @SerializedName(Constants.Fields.POSTER_PATH)
    val posterPath: String? = null,

    @SerializedName(Constants.Fields.PRODUCTION_COMPANIES)
    val productionCompanies: List<NetworkProductionCompany>? = null,

    @SerializedName(Constants.Fields.PRODUCTION_COUNTRIES)
    val productionCountries: List<NetworkProductionCountry>? = null,

    @SerializedName(Constants.Fields.SEASONS)
    val seasons: List<NetworkSeason>? = null,

    @SerializedName(Constants.Fields.SPOKEN_LANGUAGES)
    val spokenLanguages: List<NetworkSpokenLanguage>? = null,

    @SerializedName(Constants.Fields.STATUS)
    val status: String? = null,

    @SerializedName(Constants.Fields.TAGLINE)
    val tagline: String? = null,

    @SerializedName(Constants.Fields.TYPE)
    val type: String? = null,

    @SerializedName(Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double? = null,

    @SerializedName(Constants.Fields.VOTE_COUNT)
    val voteCount: Int? = null
)



