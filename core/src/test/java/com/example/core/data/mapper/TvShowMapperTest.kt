package com.example.core.data.mapper

import com.example.core.data.local.model.movie.Genre
import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.network.model.tv.TvShowNetwork
import com.example.core.domain.model.GenreModel
import com.example.core.domain.model.TvShowModel
import kotlin.test.Test
import kotlin.test.assertEquals

class TvShowMapperTest {

    private val mediaType = DatabaseMediaType.TvShow.Popular

    private val tvShowNetwork = TvShowNetwork(
        id = 101,
        name = "The Sample Show",
        overview = "A great TV show.",
        popularity = 88.8,
        firstAirDate = "2023-05-01",
        genreIds = listOf(18, 35),
        originalName = "The Original Show",
        originalLanguage = "en",
        originCountry = listOf("US"),
        voteAverage = 7.5,
        voteCount = 1200,
        posterPath = "/poster.jpg",
        backdropPath = "/backdrop.jpg"
    )

    private val tvShowEntity = TvShowEntity(
        mediaType = mediaType,
        networkId = 101,
        name = "The Sample Show",
        overview = "A great TV show.",
        popularity = 88.8,
        firstAirDate = "May 01, 2023", // assuming getFormatReleaseDate()
        genres = listOf(Genre.DRAMA,Genre.COMEDY),
        originalName = "The Original Show",
        originalLanguage = "en",
        originCountry = listOf("US"),
        voteAverage = 7.5,
        voteCount = 1200,
        posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
        seasons = 2,
        rating = 3.7
    )

    private val tvShowModel = TvShowModel(
        id = 101,
        name = "The Sample Show",
        overview = "A great TV show.",
        popularity = 88.8,
        firstAirDate = "May 01, 2023",
        genres = listOf(GenreModel.DRAMA, GenreModel.COMEDY),
        originalName = "The Original Show",
        originalLanguage = "en",
        originCountry = listOf("US"),
        voteAverage = 7.5,
        voteCount = 1200,
        posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
        rating =  3.7,
        seasons = 2
    )

    @Test
    fun `TvShowEntity maps to TvShowModel correctly`() {
        val result = tvShowEntity.asTvShowModel()
        assertEquals(tvShowModel, result)
    }

    @Test
    fun `TvShowNetwork maps to TvShowEntity correctly`() {
        val result = tvShowNetwork.asTvShowEntity(mediaType, seasons = 2)
        assertEquals(tvShowEntity, result)
    }

    @Test
    fun `TvShowNetwork maps to TvShowEntity with default seasons`() {
        val result = tvShowNetwork.asTvShowEntity(mediaType)
        assertEquals(tvShowEntity.copy(seasons = 0), result)
    }
}
