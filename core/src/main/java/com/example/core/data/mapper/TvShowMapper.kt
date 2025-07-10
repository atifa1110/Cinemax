package com.example.core.data.mapper

import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.network.asImageURL
import com.example.core.data.network.getFormatReleaseDate
import com.example.core.data.network.getRating
import com.example.core.data.network.model.tv.TvShowNetwork
import com.example.core.domain.model.TvShowModel

internal fun TvShowEntity.asTvShowModel() = TvShowModel(
    id = networkId,
    name = name,
    overview = overview,
    popularity = popularity,
    firstAirDate = firstAirDate?:"",
    genres = genres.asGenreModels(),
    originalName = originalName,
    originalLanguage = originalLanguage,
    originCountry = originCountry,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath?:"",
    backdropPath = backdropPath?:"",
    rating = rating,
    seasons = seasons
)

internal fun TvShowNetwork.asTvShowEntity(mediaType: DatabaseMediaType.TvShow) = TvShowEntity(
    mediaType = mediaType,
    networkId = id,
    name = name?:"",
    overview = overview?:"",
    popularity = popularity?:0.0,
    firstAirDate = firstAirDate?.getFormatReleaseDate(),
    genres = genreIds?.asGenres()?: emptyList(),
    originalName = originalName?:"",
    originalLanguage = originalLanguage?:"",
    originCountry = originCountry?: emptyList(),
    voteAverage = voteAverage?:0.0,
    voteCount = voteCount?:0,
    posterPath = posterPath?.asImageURL(),
    backdropPath = backdropPath?.asImageURL(),
    seasons = 0,
    rating = voteAverage?.getRating()?:0.0,
)

internal fun TvShowNetwork.asTvShowEntity(mediaType: DatabaseMediaType.TvShow,seasons: Int) = TvShowEntity(
    mediaType = mediaType,
    networkId = id,
    name = name?:"",
    overview = overview?:"",
    popularity = popularity?:0.0,
    firstAirDate = firstAirDate?.getFormatReleaseDate(),
    genres = genreIds?.asGenres()?: emptyList(),
    originalName = originalName?:"",
    originalLanguage = originalLanguage?:"",
    originCountry = originCountry?: emptyList(),
    voteAverage = voteAverage?:0.0,
    voteCount = voteCount?:0,
    posterPath = posterPath?.asImageURL(),
    backdropPath = backdropPath?.asImageURL(),
    seasons = seasons,
    rating = voteAverage?.getRating()?:0.0,
)