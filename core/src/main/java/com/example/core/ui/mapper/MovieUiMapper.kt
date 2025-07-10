package com.example.core.ui.mapper

import com.example.core.domain.model.MovieModel
import com.example.core.domain.model.SearchModel
import com.example.core.ui.model.Movie

fun MovieModel.asMovie() = Movie(
    id = id,
    title = title.toString(),
    adult = adult,
    overview = overview.toString(),
    releaseDate = releaseDate.toString(),
    genres = genres.asGenres(),
    rating = rating,
    backdropPath = backdropPath,
    posterPath = posterPath,
    profilePath = profilePath,
    mediaType = mediaType,
    runtime = runtime
)

fun SearchModel.asMovie() = Movie(
    id = id,
    title = title.toString(),
    adult = adult,
    overview = overview.toString(),
    releaseDate = releaseDate.toString(),
    genres = genres.asGenres(),
    rating = rating,
    backdropPath = backdropPath,
    posterPath = posterPath,
    profilePath = profilePath,
    mediaType = mediaType,
    runtime = runtime?:0
)

fun Movie.asSearchModel() = SearchModel(
    id = id,
    title = title.toString(),
    adult = adult,
    overview = overview.toString(),
    releaseDate = releaseDate.toString(),
    genres = genres.asGenreModel(),
    rating = rating,
    backdropPath = backdropPath,
    posterPath = posterPath,
    profilePath = profilePath,
    mediaType = mediaType,
    runtime = runtime,
    timestamp = System.currentTimeMillis(),
    popularity = 0.0,
    originalTitle = "",
    originalLanguage = "",
    voteCount = 0,
    voteAverage = 0.0,
    video = false
)

