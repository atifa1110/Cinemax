package com.example.core.data.mapper

import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.network.model.movie.MovieNetwork
import com.example.core.data.network.model.multi.MultiNetwork
import kotlin.test.Test
import kotlin.test.assertEquals

class MovieMapperTest {

    private val dummyGenreIds = listOf(28, 12)

    @Test
    fun `MovieEntity maps to MovieModel correctly`() {
        val entity = MovieEntity(
            mediaType = DatabaseMediaType.Movie.NowPlaying,
            networkId = 101,
            title = "Inception",
            overview = "Dream inside dream",
            popularity = 98.0,
            releaseDate = "16 July 2010",
            adult = false,
            genreIds = dummyGenreIds,
            originalTitle = "Inception",
            originalLanguage = "en",
            voteAverage = 8.8,
            voteCount = 20000,
            posterPath = "https://image.tmdb.org/t/p/original/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/original/backdrop.jpg",
            video = false,
            rating = 5.0,
            runtime = 148
        )

        val model = entity.asMovieModel()

        assertEquals(entity.networkId, model.id)
        assertEquals(entity.title, model.title)
        assertEquals("16 July 2010", model.releaseDate)
        assertEquals(5.0, model.rating)
        assertEquals(148, model.runtime)
    }

    @Test
    fun `MovieNetwork maps to MovieEntity without runtime correctly`() {
        val network = MovieNetwork(
            id = 101,
            title = "Interstellar",
            overview = "Space travel",
            popularity = 90.0,
            releaseDate = "2014-11-07",
            adult = false,
            genreIds = dummyGenreIds,
            originalTitle = "Interstellar",
            originalLanguage = "en",
            voteAverage = 8.5,
            voteCount = 15000,
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            video = false
        )

        val entity = network.asMovieEntity(DatabaseMediaType.Movie.Popular)

        assertEquals(101, entity.networkId)
        assertEquals("November 07, 2014", entity.releaseDate)
        assertEquals(4.2, entity.rating)
        assertEquals(NoMovieRuntimeValue, entity.runtime)
        assertEquals("https://image.tmdb.org/t/p/w500/poster.jpg", entity.posterPath)
    }

    @Test
    fun `MovieNetwork maps to MovieEntity with runtime correctly`() {
        val network = MovieNetwork(
            id = 202,
            title = "Dune",
            overview = "Spice wars",
            popularity = 80.0,
            releaseDate = "2021-10-22",
            adult = false,
            genreIds = dummyGenreIds,
            originalTitle = "Dune",
            originalLanguage = "en",
            voteAverage = 8.0,
            voteCount = 12000,
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            video = false
        )

        val entity = network.asMovieEntity(DatabaseMediaType.Movie.TopRated, runtime = 155)

        assertEquals(155, entity.runtime)
    }

    @Test
    fun `MultiNetwork maps to MovieModel correctly`() {
        val multi = MultiNetwork(
            id = 999,
            title = "Avatar",
            name = null,
            overview = "Blue aliens",
            popularity = 100.0,
            releaseDate = "2009-12-18",
            firstAirDate = null,
            adult = false,
            genreIds = dummyGenreIds,
            originalTitle = "Avatar",
            originalLanguage = "en",
            voteAverage = 7.9,
            voteCount = 18000,
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            video = false,
            mediaType = "movie",
            profilePath = null
        )

        val model = multi.asMovieModel(runtime = 161)

        assertEquals("December 18, 2009", model.releaseDate)
        assertEquals(3.9, model.rating)
        assertEquals("Movie", model.mediaType)
        assertEquals(161, model.runtime)
    }
}
