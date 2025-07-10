package com.example.core.data.local.model.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.local.util.Constants

@Entity(tableName = Constants.Tables.SEARCH)
data class SearchEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Fields.ID)
    val id: Int = 0,

    @ColumnInfo(name = Constants.Fields.MEDIA_TYPE)
    val mediaType: String,

    @ColumnInfo(name = Constants.Fields.TITLE)
    val title: String,

    @ColumnInfo(name = Constants.Fields.OVERVIEW)
    val overview: String,

    @ColumnInfo(name = Constants.Fields.POPULARITY)
    val popularity: Double,

    @ColumnInfo(name = Constants.Fields.RELEASE_DATE)
    val releaseDate: String?,

    @ColumnInfo(name = Constants.Fields.ADULT)
    val adult: Boolean,

    @ColumnInfo(name = Constants.Fields.GENRE_IDS)
    val genreEntities: List<GenreEntity>,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_TITLE)
    val originalTitle: String,

    @ColumnInfo(name = Constants.Fields.ORIGINAL_LANGUAGE)
    val originalLanguage: String,

    @ColumnInfo(name = Constants.Fields.VOTE_AVERAGE)
    val voteAverage: Double,

    @ColumnInfo(name = Constants.Fields.VOTE_COUNT)
    val voteCount: Int,

    @ColumnInfo(name = Constants.Fields.POSTER_PATH)
    val posterPath: String?,

    @ColumnInfo(name = Constants.Fields.BACKDROP_PATH)
    val backdropPath: String?,

    @ColumnInfo(name = Constants.Fields.VIDEO)
    val video: Boolean,

    @ColumnInfo(name = Constants.Fields.RATING)
    val rating: Double,

    @ColumnInfo(name = Constants.Fields.RUNTIME)
    val runtime: Int,

    @ColumnInfo(name = Constants.Fields.TIMESTAMP)
    val timestamp: Long = System.currentTimeMillis(),
)