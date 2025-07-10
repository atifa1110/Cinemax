package com.example.core.domain.model

data class SearchModel (
    val id: Int,
    val title: String,
    val overview: String,
    val popularity: Double,
    val releaseDate: String,
    val adult: Boolean = false,
    val genres: List<GenreModel>,
    val originalTitle: String,
    val originalLanguage: String,
    val voteAverage: Double,
    val voteCount: Int,
    val posterPath: String,
    val profilePath: String,
    val backdropPath: String,
    val video: Boolean,
    val rating : Double,
    val mediaType : String,
    val runtime : Int?,
    val timestamp : Long
)