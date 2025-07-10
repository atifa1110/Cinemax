package com.example.core.data.mapper

import com.example.core.data.local.model.search.SearchEntity
import com.example.core.domain.model.SearchModel

internal fun SearchEntity.asSearchModel() = SearchModel(
    id = id,
    title = title,
    overview = overview,
    popularity = popularity,
    releaseDate = releaseDate.toString(),
    adult = adult,
    genres = genreEntities.asGenreModel(),
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    voteAverage = voteAverage,
    voteCount = voteCount,
    profilePath = "",
    posterPath = posterPath.toString(),
    backdropPath = backdropPath.toString(),
    video = video,
    rating = rating,
    mediaType = mediaType,
    runtime = runtime,
    timestamp = timestamp
)

internal fun SearchModel.asSearchEntity() = SearchEntity(
    id = id,
    title = title.toString(),
    overview = overview.toString(),
    popularity = popularity,
    releaseDate = releaseDate,
    adult = adult,
    genreEntities = genres.asGenreEntity(),
    originalTitle = originalTitle.toString(),
    originalLanguage = originalLanguage.toString(),
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath,
    backdropPath = backdropPath,
    video = video,
    rating = rating,
    mediaType = mediaType.toString(),
    runtime = runtime?:0,
    timestamp = timestamp
)