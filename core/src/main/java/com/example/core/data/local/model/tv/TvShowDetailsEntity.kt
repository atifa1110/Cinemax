package com.example.core.data.local.model.tv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.movie.Genre
import com.example.core.data.local.util.Constants

@Entity(tableName = Constants.Tables.TV_SHOW_DETAILS)
data class TvShowDetailsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Constants.Fields.ID)
    val id: Int,

    @ColumnInfo(name = Constants.Fields.NAME)
    val name: String,

    @ColumnInfo(name = Constants.Fields.ADULT)
    val adult: Boolean,

    @ColumnInfo(name = Constants.Fields.BACKDROP_PATH)
    val backdropPath: String?,

    @ColumnInfo(name = Constants.Fields.EPISODE_RUN_TIME)
    val episodeRunTime: List<Int>,

    @ColumnInfo(name = Constants.Fields.FIRST_AIR_DATE)
    val firstAirDate: String?,

    @ColumnInfo(name = Constants.Fields.GENRES)
    val genres: List<Genre>,

    @ColumnInfo(name = Constants.Fields.SEASONS)
    val seasons: List<SeasonEntity>,

    @ColumnInfo(name = Constants.Fields.HOMEPAGE)
    val homepage: String,

    @ColumnInfo(name = Constants.Fields.IN_PRODUCTION)
    val inProduction: Boolean,

    @ColumnInfo(name = Constants.Fields.LANGUAGES)
    val languages: List<String>,

    @ColumnInfo(name = Constants.Fields.LAST_AIR_DATE)
    val lastAirDate: String?,

    @ColumnInfo(name = Constants.Fields.NUMBER_OF_EPISODES)
    val numberOfEpisodes: Int,

    @ColumnInfo(name = Constants.Fields.NUMBER_OF_SEASONS)
    val numberOfSeasons: Int,

    @ColumnInfo(name = Constants.Fields.ORIGIN_COUNTRY)
    val originCountry: List<String>,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_NAME)
    val originalName: String,

    @ColumnInfo(name = Constants.Fields.OVERVIEW)
    val overview: String,

    @ColumnInfo(name = Constants.Fields.POPULARITY)
    val popularity: Double,

    @ColumnInfo(name = Constants.Fields.POSTER_PATH)
    val posterPath: String?,

    @ColumnInfo(name = Constants.Fields.STATUS)
    val status: String,

    @ColumnInfo(name = Constants.Fields.TAGLINE)
    val tagline: String,

    @ColumnInfo(name = Constants.Fields.TYPE)
    val type: String,

    @ColumnInfo(name = Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double,

    @ColumnInfo(name = Constants.Fields.VOTE_COUNT)
    val voteCount: Int,

    @ColumnInfo(name = Constants.Fields.CREDITS)
    val credits: CreditsListEntity,

    @ColumnInfo(name = Constants.Fields.RATING)
    val rating: Double,
)