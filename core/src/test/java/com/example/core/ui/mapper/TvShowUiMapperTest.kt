package com.example.core.ui.mapper

import com.example.core.R
import com.example.core.data.network.asImageURL
import com.example.core.domain.model.*
import com.example.core.ui.model.CreditsList
import com.example.core.ui.model.Genre
import com.example.core.ui.model.Season
import com.example.core.ui.model.TvShow
import com.example.core.ui.model.TvShowDetails
import org.junit.Assert.assertEquals
import org.junit.Test

class TvShowUiMapperTest {

    @Test
    fun `asTvShow maps TvShowModel to TvShow correctly`() {
        val model = TvShowModel(
            id = 101,
            name = "The Office",
            overview = "Funny office sitcom",
            firstAirDate = "2005-03-24",
            genres = listOf(GenreModel.DRAMA),
            voteAverage = 8.8,
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            rating = 90.0,
            seasons = 9,
            popularity = 0.0,
            originalLanguage = "",
            originCountry = emptyList(),
            originalName = "",
            voteCount = 0
        )

        val result: TvShow = model.asTvShow()

        assertEquals(model.id, result.id)
        assertEquals(model.name, result.name)
        assertEquals(model.overview, result.overview)
        assertEquals(model.firstAirDate, result.firstAirDate)
        assertEquals(model.voteAverage, result.voteAverage, 0.001)
        assertEquals(model.genres.asGenres(), result.genres)
        assertEquals(model.posterPath, result.posterPath)
        assertEquals(model.backdropPath, result.backdropPath)
        assertEquals(model.rating, result.rating, 0.001)
        assertEquals(model.seasons, result.seasons)
    }

    @Test
    fun `asTvShowDetailModel maps TvShowDetails to TvShowDetailModel correctly`() {
        val details = TvShowDetails(
            id = 1,
            name = "Stranger Things",
            adult = false,
            backdropPath = "/backdrop.jpg",
            episodeRunTime = listOf(45),
            firstAirDate = "2016-07-15",
            genres = listOf(Genre(R.string.action)),
            seasons = listOf(
                Season(
                    airDate = "2016-07-15",
                    episodeCount = 8,
                    id = 1,
                    name = "Season 1",
                    overview = "Introduction",
                    posterPath = "/season.jpg",
                    seasonNumber = 1,
                    rating = "85"
                )
            ),
            homepage = "https://strangerthings.com",
            inProduction = true,
            languages = listOf("en"),
            lastAirDate = "2022-07-01",
            numberOfEpisodes = 34,
            numberOfSeasons = 4,
            originCountry = listOf("US"),
            originalLanguage = "en",
            originalName = "Stranger Things",
            overview = "A mystery in Hawkins",
            popularity = 123.45,
            posterPath = "/poster.jpg",
            status = "Returning Series",
            tagline = "The world is turning upside down",
            type = "Scripted",
            voteAverage = 9.1,
            voteCount = 5000,
            rating = 95.0,
            isWishListed = true,
            credits = CreditsList(emptyList(),emptyList()),

        )

        val result = details.asTvShowDetailModel()

        assertEquals(details.id, result.id)
        assertEquals(details.name, result.name)
        assertEquals(details.firstAirDate, result.firstAirDate)
        assertEquals(details.genres.asGenreModel(), result.genres)
        assertEquals(details.seasons.first().name, result.seasons.first().name)
        assertEquals(details.posterPath?.asImageURL(), result.posterPath)
        assertEquals(details.rating, result.rating, 0.001)
        assertEquals(true, result.isWishListed)
    }
}
