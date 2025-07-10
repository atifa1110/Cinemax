package com.example.core.ui.mapper

import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.ImagesListModel
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.model.VideosListModel
import com.example.core.ui.model.MovieDetails

fun MovieDetails.asMovieDetailModel() = MovieDetailModel(
    id = id,
    title = title,
    overview = overview,
    backdropPath = backdropPath,
    budget = budget,
    genres = genres.asGenreModel(),
    posterPath = posterPath,
    releaseDate = releaseDate,
    runtime = runtime,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    rating = rating,
    credits = CreditsListModel(emptyList(), emptyList()),
    images = ImagesListModel(emptyList(), emptyList()),
    videos = VideosListModel(emptyList()),
    isWishListed = isWishListed,
    adult = false,
    homepage = "",
    imdbId = "",
    originalLanguage = "",
    originalTitle = "",
    popularity = 0.0,
    revenue = 0,
    status = "",
    tagline = ""
)

fun MovieDetailModel.asMovieDetails() = MovieDetails(
    id = id,
    title = title,
    overview = overview,
    backdropPath = backdropPath,
    budget = budget,
    genres = genres.asGenres(),
    posterPath = posterPath,
    releaseDate = releaseDate,
    runtime = runtime ?: NoMovieRuntimeValue,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    rating = rating,
    credits = credits.asCredits(),
    images = images.asImages(),
    videos = videos.asVideos(),
    isWishListed = isWishListed
)

const val NoMovieRuntimeValue = 0