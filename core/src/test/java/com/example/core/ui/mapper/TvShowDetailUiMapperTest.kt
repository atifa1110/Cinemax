package com.example.core.ui.mapper

import com.example.core.data.network.asImageURL
import com.example.core.domain.model.*
import org.junit.Assert.assertEquals
import org.junit.Test

class TvShowDetailUiMapperTest {

    @Test
    fun `asTvShowDetails maps TvShowDetailModel to TvShowDetails correctly`() {
        val model = TvShowDetailModel(
            id = 123,
            name = "Breaking Bad",
            adult = false,
            backdropPath = "/backdrop.jpg",
            episodeRunTime = listOf(50),
            firstAirDate = "2008-01-20",
            genres = listOf(GenreModel.DRAMA),
            seasons = listOf(
                SeasonModel(
                    airDate = "2008-01-20",
                    episodeCount = 7,
                    id = 1,
                    name = "Season 1",
                    overview = "Season overview",
                    posterPath = "/season1.jpg",
                    seasonNumber = 1,
                    rating = "90"
                )
            ),
            homepage = "https://www.breakingbad.com",
            inProduction = false,
            languages = listOf("en"),
            lastAirDate = "2013-09-29",
            numberOfEpisodes = 62,
            numberOfSeasons = 5,
            originCountry = listOf("US"),
            originalLanguage = "en",
            originalName = "Breaking Bad",
            overview = "High school chemistry teacher turns meth kingpin.",
            popularity = 99.9,
            posterPath = "/poster.jpg",
            status = "Ended",
            tagline = "All bad things must come to an end.",
            type = "Scripted",
            voteAverage = 9.5,
            voteCount = 10000,
            credits = CreditsListModel(emptyList(), emptyList()),
            rating = 95.0,
            isWishListed = true
        )

        val result = model.asTvShowDetails()

        assertEquals(model.id, result.id)
        assertEquals(model.name, result.name)
        assertEquals(model.voteAverage, result.voteAverage, 0.001)
        assertEquals(model.genres.asGenres(), result.genres)
        assertEquals(model.seasons.first().name, result.seasons.first().name)
        assertEquals(model.posterPath?.asImageURL(), result.posterPath) // assuming asImageURL appends "?image_url"
    }
}