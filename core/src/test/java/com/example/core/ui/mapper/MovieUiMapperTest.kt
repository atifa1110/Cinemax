package com.example.core.ui.mapper

import com.example.core.R
import com.example.core.domain.model.*
import com.example.core.ui.model.*
import org.junit.Assert.*
import org.junit.Test

class MovieUiMapperTest {

    @Test
    fun `asMovie should map MovieModel to Movie`() {
        val model = MovieModel(
            id = 1,
            title = "Inception",
            adult = false,
            overview = "A mind-bending thriller.",
            releaseDate = "2010-07-16",
            genres = listOf(GenreModel.ACTION),
            rating = 4.8,
            backdropPath = "/backdrop.jpg",
            posterPath = "/poster.jpg",
            profilePath = "",
            mediaType = "movie",
            runtime = 148,
            popularity = 0.0,
            originalTitle = "",
            originalLanguage = "",
            video = false,
            voteAverage = 0.0,
            voteCount = 0
        )

        val movie = model.asMovie()

        assertEquals(model.id, movie.id)
        assertEquals(model.title, movie.title)
        assertEquals(model.genres.asGenres(), movie.genres)
        assertEquals(model.runtime, movie.runtime)
    }

    @Test
    fun `asMovie should map SearchModel to Movie`() {
        val model = SearchModel(
            id = 2,
            title = "Avatar",
            adult = false,
            overview = "A sci-fi epic.",
            releaseDate = "2009-12-18",
            genres = listOf(GenreModel.ACTION),
            rating = 4.0,
            backdropPath = "/avatar_backdrop.jpg",
            posterPath = "/avatar_poster.jpg",
            profilePath = "",
            mediaType = "movie",
            runtime = 162,
            timestamp = System.currentTimeMillis(),
            popularity = 10.0,
            originalTitle = "Avatar",
            originalLanguage = "en",
            voteCount = 10000,
            voteAverage = 8.0,
            video = false
        )

        val movie = model.asMovie()

        assertEquals(model.id, movie.id)
        assertEquals(model.title, movie.title)
        assertEquals(model.genres.asGenres(), movie.genres)
        assertEquals(model.runtime, movie.runtime)
    }

    @Test
    fun `asSearchModel should map Movie to SearchModel`() {
        val movie = Movie(
            id = 3,
            title = "The Matrix",
            adult = false,
            overview = "Simulation world action.",
            releaseDate = "1999-03-31",
            genres = listOf(Genre(R.string.action)),
            rating = 4.7,
            backdropPath = "/matrix_backdrop.jpg",
            posterPath = "/matrix_poster.jpg",
            profilePath = "",
            mediaType = "movie",
            runtime = 136
        )

        val model = movie.asSearchModel()

        assertEquals(movie.id, model.id)
        assertEquals(movie.title, model.title)
        assertEquals(movie.genres.asGenreModel(), model.genres)
        assertEquals(movie.runtime, model.runtime)
    }
}
