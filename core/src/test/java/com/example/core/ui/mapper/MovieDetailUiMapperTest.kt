package com.example.core.ui.mapper

import com.example.core.R
import com.example.core.domain.model.*
import com.example.core.ui.model.*
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailUiMapperTest {

    @Test
    fun `asMovieDetailModel should correctly map MovieDetails to MovieDetailModel`() {
        val uiModel = MovieDetails(
            id = 101,
            title = "Interstellar",
            overview = "Space exploration.",
            backdropPath = "/backdrop.jpg",
            budget = 165000000,
            genres = listOf(Genre(R.string.action)),
            posterPath = "/poster.jpg",
            releaseDate = "2014-11-07",
            runtime = 169,
            video = false,
            voteAverage = 8.6,
            voteCount = 14000,
            rating = 4.5,
            credits = CreditsList(emptyList(), emptyList()),
            images = ImagesList(emptyList(), emptyList()),
            videos = VideosList(emptyList()),
            isWishListed = true
        )

        val domainModel = uiModel.asMovieDetailModel()

        assertEquals(uiModel.id, domainModel.id)
        assertEquals(uiModel.title, domainModel.title)
        assertEquals(uiModel.overview, domainModel.overview)
        assertEquals(uiModel.posterPath, domainModel.posterPath)
        assertEquals(uiModel.runtime, domainModel.runtime)
        assertEquals(uiModel.genres.asGenreModel(), domainModel.genres)
        assertEquals(uiModel.isWishListed, domainModel.isWishListed)
    }

    @Test
    fun `asMovieDetails should correctly map MovieDetailModel to MovieDetails`() {
        val domainModel = MovieDetailModel(
            id = 101,
            title = "Inception",
            overview = "Dream within a dream.",
            backdropPath = "/inception.jpg",
            budget = 160000000,
            genres = listOf(GenreModel.ACTION),
            posterPath = "/poster.jpg",
            releaseDate = "2010-07-16",
            runtime = 148,
            video = false,
            voteAverage = 8.8,
            voteCount = 20000,
            rating = 4.8,
            credits = CreditsListModel(emptyList(), emptyList()),
            images = ImagesListModel(emptyList(), emptyList()),
            videos = VideosListModel(emptyList()),
            isWishListed = false,
            adult = false,
            homepage = "",
            imdbId = "",
            originalLanguage = "",
            originalTitle = "",
            popularity = 0.0,
            revenue = 0,
            status = "",
            tagline = ""
        )

        val uiModel = domainModel.asMovieDetails()

        assertEquals(domainModel.id, uiModel.id)
        assertEquals(domainModel.title, uiModel.title)
        assertEquals(domainModel.overview, uiModel.overview)
        assertEquals(domainModel.posterPath, uiModel.posterPath)
        assertEquals(domainModel.runtime, uiModel.runtime)
        assertEquals(domainModel.genres.asGenres(), uiModel.genres)
        assertEquals(domainModel.isWishListed, uiModel.isWishListed)
    }
}
