package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.movie.Genre
import com.example.core.data.local.model.tv.TvShowDetailsEntity
import com.example.core.data.network.model.genre.GenreNetwork
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.domain.model.GenreModel
import com.example.core.domain.model.TvShowDetailModel
import kotlin.test.Test
import kotlin.test.assertEquals

class TvShowDetailMapperTest {

    private val dummyCredits = CreditsListEntity(cast = emptyList(), crew = emptyList())

    private val entity = TvShowDetailsEntity(
        id = 1,
        name = "Test Show",
        adult = false,
        backdropPath = "/backdrop.jpg",
        episodeRunTime = listOf(50),
        firstAirDate = "January 01, 2023",
        genres = listOf(Genre.ACTION),
        seasons = listOf(),
        homepage = "https://example.com",
        inProduction = false,
        languages = listOf("en"),
        lastAirDate = "2023-12-31",
        numberOfEpisodes = 10,
        numberOfSeasons = 1,
        originCountry = listOf("US"),
        originalLanguage = "en",
        originalName = "Original Show",
        overview = "A test show.",
        popularity = 99.0,
        posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
        status = "Ended",
        tagline = "Just a test",
        type = "Scripted",
        voteAverage = 8.5,
        voteCount = 100,
        credits = dummyCredits,
        rating = 4.2
    )

    private val network = TvShowDetailNetwork(
        id = 1,
        name = "Test Show",
        adult = false,
        backdropPath = "/backdrop.jpg",
        episodeRunTime = listOf(50),
        firstAirDate = "2023-01-01",
        genres = listOf(GenreNetwork(28,"Action")),
        seasons = listOf(),
        homepage = "https://example.com",
        inProduction = false,
        languages = listOf("en"),
        lastAirDate = "2023-12-31",
        numberOfEpisodes = 10,
        numberOfSeasons = 1,
        originCountry = listOf("US"),
        originalLanguage = "en",
        originalName = "Original Show",
        overview = "A test show.",
        popularity = 99.0,
        posterPath = "/poster.jpg",
        status = "Ended",
        tagline = "Just a test",
        type = "Scripted",
        voteAverage = 8.5,
        voteCount = 100,
        credits = null
    )

    @Test
    fun `TvShowDetailNetwork maps to TvShowDetailsEntity correctly`() {
        val result = network.asTvShowDetailsEntity()
        assertEquals(entity.copy(credits = dummyCredits), result)
    }

    @Test
    fun `TvShowDetailsEntity maps to TvShowDetailModel correctly`() {
        val expectedModel = TvShowDetailModel(
            id = 1,
            name = "Test Show",
            adult = false,
            backdropPath = "/backdrop.jpg",
            episodeRunTime = listOf(50),
            firstAirDate = "January 01, 2023",
            genres = listOf(GenreModel.ACTION),
            seasons = listOf(),
            homepage = "https://example.com",
            inProduction = false,
            languages = listOf("en"),
            lastAirDate = "2023-12-31",
            numberOfEpisodes = 10,
            numberOfSeasons = 1,
            originCountry = listOf("US"),
            originalLanguage = "en",
            originalName = "Original Show",
            overview = "A test show.",
            popularity = 99.0,
            posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            status = "Ended",
            tagline = "Just a test",
            type = "Scripted",
            voteAverage = 8.5,
            voteCount = 100,
            credits = dummyCredits.asCreditsModel(),
            rating = 4.2,
            isWishListed = true
        )

        val result = entity.asTvShowDetailsModel(isWishListed = true)
        assertEquals(expectedModel, result)
    }
}
