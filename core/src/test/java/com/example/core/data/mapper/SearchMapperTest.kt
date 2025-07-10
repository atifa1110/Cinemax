package com.example.core.data.mapper

import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.local.model.search.SearchEntity
import com.example.core.domain.model.GenreModel
import com.example.core.domain.model.SearchModel
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchMapperTest {

    private val dummyGenresEntity = listOf(
        GenreEntity(name = "action")
    )

    private val dummyGenresModel = listOf(
        GenreModel.ACTION
    )

    @Test
    fun `SearchEntity maps to SearchModel correctly`() {
        val entity = SearchEntity(
            id = 123,
            title = "Example Title",
            overview = "Overview here",
            popularity = 99.9,
            releaseDate = "2025-01-01",
            adult = false,
            genreEntities = dummyGenresEntity,
            originalTitle = "Original Title",
            originalLanguage = "en",
            voteAverage = 8.7,
            voteCount = 1500,
            posterPath = "posterPath.jpg",
            backdropPath = "backdropPath.jpg",
            video = false,
            rating = 4.0,
            mediaType = "movie",
            runtime = 120,
            timestamp = 999999L
        )

        val model = entity.asSearchModel()

        assertEquals(entity.id, model.id)
        assertEquals(entity.title, model.title)
        assertEquals(entity.overview, model.overview)
        assertEquals("2025-01-01", model.releaseDate)
        assertEquals(entity.voteAverage, model.voteAverage)
        assertEquals(entity.mediaType, model.mediaType)
        assertEquals(120, model.runtime)
        assertEquals(999999L, model.timestamp)
    }

    @Test
    fun `SearchModel maps to SearchEntity correctly`() {
        val model = SearchModel(
            id = 321,
            title = "Another Title",
            overview = "Another overview",
            popularity = 77.7,
            releaseDate = "2024-12-31",
            adult = true,
            genres = dummyGenresModel,
            originalTitle = "Original Title 2",
            originalLanguage = "id",
            voteAverage = 7.5,
            voteCount = 1200,
            posterPath = "poster.jpg",
            backdropPath = "backdrop.jpg",
            video = true,
            rating = 3.5,
            mediaType = "tv",
            runtime = 100,
            timestamp = 888888L,
            profilePath = ""
        )

        val entity = model.asSearchEntity()

        assertEquals(model.id, entity.id)
        assertEquals(model.title, entity.title)
        assertEquals(model.voteAverage, entity.voteAverage)
        assertEquals(model.genres.asGenreEntity(), entity.genreEntities)
        assertEquals(model.timestamp, entity.timestamp)
        assertEquals(model.runtime, entity.runtime)
    }
}
