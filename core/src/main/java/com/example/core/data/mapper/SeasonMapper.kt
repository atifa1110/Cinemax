package com.example.core.data.mapper

import com.example.core.data.local.model.tv.SeasonEntity
import com.example.core.data.network.asImageURL
import com.example.core.data.network.getFormatReleaseDate
import com.example.core.data.network.getPercentageRating
import com.example.core.data.network.model.tv.NetworkSeason
import com.example.core.domain.model.SeasonModel

internal fun NetworkSeason.asSeasonEntity() = SeasonEntity(
    airDate = airDate?.getFormatReleaseDate()?:"Coming Soon",
    episodeCount = episodeCount,
    id = id,
    name = name,
    overview = overview,
    posterPath = posterPath?.asImageURL()?:"",
    seasonNumber = seasonNumber,
    rating = voteAverage.getPercentageRating().toString()
)

internal fun SeasonEntity.asSeasonModel() = SeasonModel(
    airDate = airDate,
    episodeCount = episodeCount,
    id = id,
    name = name,
    overview = overview,
    posterPath = posterPath,
    seasonNumber = seasonNumber,
    rating = rating
)

internal fun List<NetworkSeason>.asSeasonsEntity() = map(NetworkSeason::asSeasonEntity)
internal fun List<SeasonEntity>.asSeasonModels() = map(SeasonEntity::asSeasonModel)