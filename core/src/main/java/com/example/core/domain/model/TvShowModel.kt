package com.example.core.domain.model

data class TvShowModel (
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
    val firstAirDate: String,
    val genres: List<GenreModel>,
    val originalName: String,
    val originalLanguage: String,
    val originCountry: List<String>,
    val voteAverage: Double,
    val voteCount: Int,
    val posterPath: String,
    val backdropPath: String,
    val rating : Double,
    val seasons : Int
)