package com.example.core.ui.mapper

import com.example.core.data.network.asImageURL
import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.model.TvShowModel
import com.example.core.ui.model.TvShow
import com.example.core.ui.model.TvShowDetails

fun TvShowDetails.asTvShowDetailModel() = TvShowDetailModel(
    id = id,
    name = name,
    adult = adult,
    backdropPath = backdropPath,
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    genres = genres.asGenreModel(),
    seasons = seasons.asSeasonModel(),
    homepage = homepage,
    inProduction = inProduction,
    languages = languages,
    lastAirDate = lastAirDate,
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalName = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath?.asImageURL(),
    status = status,
    tagline = tagline,
    type = type,
    voteAverage = voteAverage,
    voteCount = voteCount,
    credits = CreditsListModel(emptyList(), emptyList()),
    rating = rating,
    isWishListed = isWishListed
)

fun TvShowModel.asTvShow() = TvShow(
    id = id,
    name = name,
    overview = overview,
    firstAirDate = firstAirDate,
    genres = genres.asGenres(),
    voteAverage = voteAverage,
    posterPath = posterPath,
    backdropPath = backdropPath,
    rating = rating,
    seasons = seasons
)
