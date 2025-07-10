package com.example.core.ui.model

data class Movie(
    val id: Int,
    val title: String,
    val adult: Boolean,
    val overview: String,
    val mediaType : String,
    val releaseDate: String,
    val genres: List<Genre>,
    val rating: Double,
    val backdropPath : String,
    val posterPath: String,
    val profilePath: String,
    val runtime : Int
)