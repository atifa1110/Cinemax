package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.*
import com.example.core.data.local.model.movie.Genre
import com.example.core.data.network.model.genre.GenreNetwork
import com.example.core.data.network.model.movie.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MovieDetailMapperTest {

    @Test
    fun `MovieDetailNetwork maps to MovieDetailsEntity correctly`() {
        val networkModel = MovieDetailNetwork(
            id = 1,
            adult = false,
            backdropPath = "/backdrop.jpg",
            budget = 100000,
            genres = listOf(GenreNetwork(id = 18, name = "Drama")),
            homepage = "https://movie.com",
            imdbId = "tt1234567",
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Overview",
            popularity = 9.5,
            posterPath = "/poster.jpg",
            releaseDate = "2024-05-01",
            revenue = 200000,
            runtime = 120,
            status = "Released",
            tagline = "Tagline",
            title = "Title",
            video = false,
            voteAverage = 7.8,
            voteCount = 1000,
            credits = NetworkListCredits(cast = listOf(), crew = listOf()),
            videos = NetworkListVideos(listOf()),
            images = NetworkListImages(backdrops = listOf(), posters = listOf())
        )

        val entity = networkModel.asMovieDetailsEntity()

        assertEquals(networkModel.id, entity.id)
        assertEquals(networkModel.adult, entity.adult)
        assertEquals("/poster.jpg", networkModel.posterPath)
        assertEquals(7.8, entity.voteAverage)
        assertEquals(3.9, entity.rating)
        assertNotNull(entity.credits)
        assertNotNull(entity.videos)
        assertNotNull(entity.images)
    }

    @Test
    fun `MovieDetailsEntity maps to MovieDetailModel correctly`() {
        val entity = MovieDetailsEntity(
            id = 1,
            adult = false,
            backdropPath = "/backdrop.jpg",
            budget = 100000,
            genreEntities = listOf(Genre.ACTION),
            homepage = "https://movie.com",
            imdbId = "tt1234567",
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Overview",
            popularity = 9.5,
            posterPath = "https://image.tmdb.org/t/p/original/poster.jpg",
            releaseDate = "01 May 2024",
            revenue = 200000,
            runtime = 120,
            status = "Released",
            tagline = "Tagline",
            title = "Title",
            video = false,
            voteAverage = 7.8,
            voteCount = 1000,
            rating = 4.0,
            credits = CreditsListEntity(listOf(), listOf()),
            videos = VideosListEntity(listOf()),
            images = ImagesListEntity(listOf(), listOf())
        )

        val model = entity.asMovieDetailsModel(isWishListed = true)

        assertEquals(entity.id, model.id)
        assertEquals(entity.title, model.title)
        assertEquals(true, model.isWishListed)
        assertNotNull(model.credits)
        assertNotNull(model.videos)
        assertNotNull(model.images)
    }
}
