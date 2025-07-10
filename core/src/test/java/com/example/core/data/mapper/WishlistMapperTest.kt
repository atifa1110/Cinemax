package com.example.core.data.mapper

import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.local.model.wishlist.WishlistEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.GenreModel
import com.example.core.domain.model.ImagesListModel
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.model.VideosListModel
import com.example.core.domain.model.WishlistModel
import kotlin.test.Test
import kotlin.test.assertEquals

class WishlistMapperTest {

    private val genreModel = GenreModel.DRAMA
    private val genreEntity = GenreEntity(id = 18, name = "Drama")

    private val movieDetail = MovieDetailModel(
        id = 671,
        adult = false,
        backdropPath = "/back.jpg",
        budget = 125000000,
        genres = listOf(GenreModel.DRAMA),
        homepage = "",
        imdbId = "tt0241527",
        originalLanguage = "en",
        originalTitle = "Harry Potter",
        overview = "",
        popularity = 239.401,
        posterPath = "/poster.jpg",
        releaseDate = "2001-11-16",
        revenue = 976475550L,
        runtime = 152,
        status = "Released",
        tagline = "Let the magic begin.",
        title = "Harry Potter",
        video = false,
        voteAverage = 7.907,
        voteCount = 27620,
        credits = CreditsListModel(emptyList(), emptyList()),
        images = ImagesListModel(emptyList(), emptyList()),
        videos = VideosListModel(emptyList()),
        rating = 0.0,
        isWishListed = false
    )

    private val tvShowDetail = TvShowDetailModel(
        id = 200,
        name = "Squid Game",
        adult = false,
        backdropPath = "/back.jpg",
        credits = CreditsListModel(emptyList(), emptyList()),
        episodeRunTime = emptyList(),
        firstAirDate = "2021-09-17",
        genres = listOf(GenreModel.DRAMA),
        homepage = "https://www.netflix.com/title/81040344",
        inProduction= true,
        languages = emptyList(),
        lastAirDate= "2024-12-26",
        numberOfEpisodes = 16,
        numberOfSeasons = 3,
        originCountry = emptyList(),
        originalLanguage = "ko",
        originalName = "오징어 게임",
        overview = "",
        popularity = 12404.255,
        posterPath = "/poster.jpg",
        seasons = emptyList(),
        status = "Returning Series",
        tagline = "45.6 billion won is child's play.",
        type = "Scripted",
        voteAverage = 7.8,
        voteCount = 14677,
        rating = 0.0,
        isWishListed = false
    )

    @Test
    fun `WishlistEntity maps to WishlistModel correctly`() {
        val result = DatabaseMediaType.Wishlist.Movie.asWishlistEntity(movieDetail)

        val expected = WishlistModel(
            id = 671,
            mediaType = MediaTypeModel.Wishlist.Movie,
            title = "Harry Potter",
            genre = listOf(genreModel),
            rating = 0.0,
            posterPath = "/poster.jpg",
            isWishListed = false
        )

        assertEquals(expected, result.asWishlistModel())
    }

    @Test
    fun `MovieDetailModel maps to WishlistEntity correctly`() {
        val result = DatabaseMediaType.Wishlist.Movie.asWishlistEntity(movieDetail)

        val expected = WishlistEntity(
            mediaType = DatabaseMediaType.Wishlist.Movie,
            networkId = 671,
            title = "Harry Potter",
            genreEntities = listOf(GenreEntity(0,"drama")),
            rating = 0.0,
            posterPath = "/poster.jpg",
            isWishListed = false
        )

        assertEquals(expected, result)
    }

    @Test
    fun `TvShowDetailModel maps to WishlistEntity correctly`() {
        val result = DatabaseMediaType.Wishlist.TvShow.asWishlistEntity(tvShowDetail)

        val expected = WishlistEntity(
            mediaType = DatabaseMediaType.Wishlist.TvShow,
            networkId = 200,
            title = "Squid Game",
            genreEntities = listOf(GenreEntity(0,"drama")),
            rating = 0.0,
            posterPath = "/poster.jpg",
            isWishListed = false
        )

        assertEquals(expected, result)
    }
}
