package com.example.core.data.local.model.tv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.local.model.movie.Genre
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.local.util.Constants


@Entity(tableName = Constants.Tables.TV_SHOWS)
data class TvShowEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Fields.ID)
    val id: Int = 0,

    @ColumnInfo(name = Constants.Fields.MEDIA_TYPE)
    val mediaType: DatabaseMediaType.TvShow,

    @ColumnInfo(name = Constants.Fields.NETWORK_ID)
    val networkId: Int,

    @ColumnInfo(name = Constants.Fields.NAME)
    val name: String,

    @ColumnInfo(name = Constants.Fields.OVERVIEW)
    val overview: String,

    @ColumnInfo(name = Constants.Fields.POPULARITY)
    val popularity: Double,

    @ColumnInfo(name = Constants.Fields.FIRST_AIR_DATE)
    val firstAirDate: String?,

    @ColumnInfo(name = Constants.Fields.GENRE_IDS)
    val genres: List<Genre>,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_NAME)
    val originalName: String,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String,

    @ColumnInfo(name = Constants.Fields.ORIGIN_COUNTRY)
    val originCountry: List<String>,

    @ColumnInfo(name = Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double,

    @ColumnInfo(name = Constants.Fields.VOTE_COUNT)
    val voteCount: Int,

    @ColumnInfo(name = Constants.Fields.POSTER_PATH)
    val posterPath: String?,

    @ColumnInfo(name = Constants.Fields.BACKDROP_PATH)
    val backdropPath: String?,

    @ColumnInfo(name = Constants.Fields.SEASONS)
    val seasons: Int,

    @ColumnInfo(name = Constants.Fields.RATING)
    val rating: Double
)