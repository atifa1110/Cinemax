package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.tv.TvShowDetailsEntity
import com.example.core.data.network.asImageURL
import com.example.core.data.network.getFormatReleaseDate
import com.example.core.data.network.getRating
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.domain.model.TvShowDetailModel

fun TvShowDetailsEntity.asTvShowDetailsModel(isWishListed: Boolean) = TvShowDetailModel(
    id = id,
    name = name,
    adult = adult,
    backdropPath = backdropPath,
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    genres = genres.asGenreModels(),
    seasons = seasons.asSeasonModels(),
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
    posterPath = posterPath,
    status = status,
    tagline = tagline,
    type = type,
    voteAverage = voteAverage,
    voteCount = voteCount,
    credits = credits.asCreditsModel(),
    rating = rating,
    isWishListed = isWishListed,
)

fun TvShowDetailNetwork.asTvShowDetailsEntity() = TvShowDetailsEntity(
    id = id,
    name = name,
    adult = adult,
    backdropPath = backdropPath,
    episodeRunTime = episodeRunTime?: listOf(),
    firstAirDate = firstAirDate?.getFormatReleaseDate(),
    genres = genres?.asGenreNetwork()?: emptyList(),
    seasons = seasons?.asSeasonsEntity()?: emptyList(),
    homepage = homepage?:"",
    inProduction = inProduction,
    languages = languages?: emptyList(),
    lastAirDate = lastAirDate,
    numberOfEpisodes = numberOfEpisodes?:0,
    numberOfSeasons = numberOfSeasons?:0,
    originCountry = originCountry?: emptyList(),
    originalLanguage = originalLanguage?:"",
    originalName = originalName?:"",
    overview = overview?:"",
    popularity = popularity?:0.0,
    posterPath = posterPath?.asImageURL(),
    status = status?:"",
    tagline = tagline?:"",
    type = type?:"",
    voteAverage = voteAverage?:0.0,
    voteCount = voteCount?:0,
    credits = credits?.asCredits()?: CreditsListEntity(emptyList(), emptyList()),
    rating = voteAverage?.getRating()?:0.0,
)