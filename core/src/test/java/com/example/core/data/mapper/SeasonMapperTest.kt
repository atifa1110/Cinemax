package com.example.core.data.mapper

import com.example.core.data.local.model.tv.SeasonEntity
import com.example.core.data.network.model.tv.NetworkSeason
import com.example.core.domain.model.SeasonModel
import kotlin.test.Test
import kotlin.test.assertEquals

class SeasonMapperTest {

    private val networkSeason = NetworkSeason(
        airDate = "2023-10-01",
        episodeCount = 10,
        id = 1001,
        name = "Season 1",
        overview = "Intro season.",
        posterPath = "/poster.jpg",
        seasonNumber = 1,
        voteAverage = 8.0
    )

    private val seasonEntity = SeasonEntity(
        airDate = "October 01, 2023",
        episodeCount = 10,
        id = 1001,
        name = "Season 1",
        overview = "Intro season.",
        posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
        seasonNumber = 1,
        rating = "80"
    )

    private val seasonModel = SeasonModel(
        airDate = "October 01, 2023",
        episodeCount = 10,
        id = 1001,
        name = "Season 1",
        overview = "Intro season.",
        posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
        seasonNumber = 1,
        rating = "80"
    )

    @Test
    fun `NetworkSeason maps to SeasonEntity correctly`() {
        val result = networkSeason.asSeasonEntity()
        assertEquals(seasonEntity, result)
    }

    @Test
    fun `SeasonEntity maps to SeasonModel correctly`() {
        val result = seasonEntity.asSeasonModel()
        assertEquals(seasonModel, result)
    }

    @Test
    fun `List of NetworkSeason maps to list of SeasonEntity`() {
        val networkSeasons = listOf(networkSeason)
        val expected = listOf(seasonEntity)

        val result = networkSeasons.asSeasonsEntity()
        assertEquals(expected, result)
    }

    @Test
    fun `List of SeasonEntity maps to list of SeasonModel`() {
        val entities = listOf(seasonEntity)
        val expected = listOf(seasonModel)

        val result = entities.asSeasonModels()
        assertEquals(expected, result)
    }
}
