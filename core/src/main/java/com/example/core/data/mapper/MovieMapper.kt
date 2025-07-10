package com.example.core.data.mapper

import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.network.asImageURL
import com.example.core.data.network.asMediaType
import com.example.core.data.network.getFormatReleaseDate
import com.example.core.data.network.getRating
import com.example.core.data.network.model.movie.MovieNetwork
import com.example.core.data.network.model.multi.MultiNetwork
import com.example.core.domain.model.MovieModel

internal fun MovieEntity.asMovieModel() = MovieModel(
    id = networkId,
    title = title,
    overview = overview,
    popularity = popularity,
    releaseDate = releaseDate.toString(),
    adult = adult,
    genres = genreIds?.asGenreModels()?:emptyList(),
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath.toString(),
    backdropPath = backdropPath.toString(),
    video = video,
    rating = rating,
    mediaType = type,
    profilePath = "",
    runtime = runtime
)

internal fun MovieNetwork.asMovieEntity(mediaType: DatabaseMediaType.Movie) = MovieEntity(
    mediaType = mediaType,
    networkId = id?:0,
    title = title?:"",
    overview = overview.toString(),
    popularity = popularity?:0.0,
    releaseDate = releaseDate?.getFormatReleaseDate(),
    adult = adult,
    genreIds = genreIds,
    originalTitle = originalTitle.toString(),
    originalLanguage = originalLanguage.toString(),
    voteAverage = voteAverage?:0.0,
    voteCount = voteCount?:0,
    posterPath = posterPath?.asImageURL(),
    backdropPath = backdropPath?.asImageURL(),
    video = video,
    rating = voteAverage?.getRating()?:0.0,
    runtime = NoMovieRuntimeValue
)

internal fun MultiNetwork.asMovieModel(runtime: Int) = MovieModel(
    id = id?:0,
    title = (title ?: name).orEmpty(),
    overview = overview.toString(),
    popularity = popularity?:0.0,
    releaseDate = (releaseDate ?: firstAirDate)?.getFormatReleaseDate() ?: "",
    adult = adult,
    genres = genreIds?.asGenreModels()?:emptyList(),
    originalTitle = originalTitle.toString(),
    originalLanguage = originalLanguage.toString(),
    voteAverage = voteAverage?:0.0,
    voteCount = voteCount?:0,
    posterPath = posterPath?.asImageURL()?:"",
    backdropPath = backdropPath?.asImageURL()?:"",
    video = video,
    rating = voteAverage?.getRating()?:0.0,
    mediaType = mediaType?.asMediaType()?:"",
    profilePath = "",
    runtime = runtime
)

internal fun MovieNetwork.asMovieEntity(mediaType: DatabaseMediaType.Movie, runtime: Int?) = MovieEntity(
    mediaType = mediaType,
    networkId = id?:0,
    title = title.toString(),
    overview = overview.toString(),
    popularity = popularity?:0.0,
    releaseDate = releaseDate?.getFormatReleaseDate(),
    adult = adult,
    genreIds = genreIds,
    originalTitle = originalTitle.toString(),
    originalLanguage = originalLanguage.toString(),
    voteAverage = voteAverage?:0.0,
    voteCount = voteCount?:0,
    posterPath = posterPath?.asImageURL(),
    backdropPath = backdropPath?.asImageURL(),
    video = video,
    rating = voteAverage?.getRating()?:0.0,
    runtime = runtime?: NoMovieRuntimeValue
)

const val NoMovieRuntimeValue = 0