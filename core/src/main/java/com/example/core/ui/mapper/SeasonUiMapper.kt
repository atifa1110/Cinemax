package com.example.core.ui.mapper

import com.example.core.domain.model.SeasonModel
import com.example.core.ui.model.Season


internal fun List<SeasonModel>.asSeasons() = map(SeasonModel::asSeason)

private fun SeasonModel.asSeason() = Season(
    airDate = airDate,
    episodeCount = episodeCount,
    id = id,
    name = name,
    overview = overview,
    posterPath = posterPath,
    seasonNumber = seasonNumber,
    rating = rating
)

internal fun List<Season>.asSeasonModel() = map(Season::asSeasonModel)

private fun Season.asSeasonModel() = SeasonModel(
    airDate = airDate,
    episodeCount = episodeCount,
    id = id,
    name = name,
    overview = overview,
    posterPath = posterPath,
    seasonNumber = seasonNumber,
    rating = rating
)
