package com.example.core.ui.model

data class MovieDetails (
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val backdropPath: String? = "",
    val budget: Int = 0,
    val genres: List<Genre> = emptyList(),
    val posterPath: String? = "",
    val releaseDate: String? = "",
    val runtime: Int = 0,
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val credits: CreditsList = CreditsList(emptyList(),emptyList()),
    val rating : Double = 0.0,
    val images : ImagesList = ImagesList(emptyList(),emptyList()),
    val videos : VideosList = VideosList(emptyList()),
    val isWishListed: Boolean = false
)