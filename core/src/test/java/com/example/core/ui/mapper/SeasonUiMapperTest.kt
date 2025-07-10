package com.example.core.ui.mapper

import com.example.core.domain.model.SeasonModel
import com.example.core.ui.model.Season
import org.junit.Assert.*
import org.junit.Test

class SeasonUiMapperTest {

    @Test
    fun `asSeasons should map List of SeasonModel to List of Season`() {
        val seasonModels = listOf(
            SeasonModel(
                airDate = "2020-01-01",
                episodeCount = 10,
                id = 1,
                name = "Season 1",
                overview = "Overview 1",
                posterPath = "/poster1.jpg",
                seasonNumber = 1,
                rating = "90"
            ),
            SeasonModel(
                airDate = "2021-01-01",
                episodeCount = 8,
                id = 2,
                name = "Season 2",
                overview = "Overview 2",
                posterPath = "/poster2.jpg",
                seasonNumber = 2,
                rating = "85"
            )
        )

        val seasons = seasonModels.asSeasons()

        assertEquals(seasonModels.size, seasons.size)
        assertEquals(seasonModels[0].name, seasons[0].name)
        assertEquals(seasonModels[1].posterPath, seasons[1].posterPath)
    }

    @Test
    fun `asSeasonModel should map List of Season to List of SeasonModel`() {
        val seasons = listOf(
            Season(
                airDate = "2020-01-01",
                episodeCount = 10,
                id = 1,
                name = "Season 1",
                overview = "Overview 1",
                posterPath = "/poster1.jpg",
                seasonNumber = 1,
                rating = "90"
            )
        )

        val models = seasons.asSeasonModel()

        assertEquals(seasons.size, models.size)
        assertEquals(seasons[0].overview, models[0].overview)
        assertEquals(seasons[0].rating, models[0].rating)
    }
}
