package com.example.core.data.local.source

import com.example.core.data.local.dao.wishlist.WishlistDao
import com.example.core.data.local.model.wishlist.WishlistEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.mapper.asWishlistEntity
import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.ImagesListModel
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.model.VideosListModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WishlistDatabaseSourceTest {

    private lateinit var wishlistDao: WishlistDao
    private lateinit var wishlistDatabaseSource: WishlistDatabaseSource

    @Before
    fun setUp() {
        wishlistDao = mockk()
        wishlistDatabaseSource = WishlistDatabaseSource(wishlistDao)
    }

    val movie = MovieDetailModel(
        id = 0, adult = false, backdropPath = "", budget = 0, genres = listOf(),
        homepage = "", imdbId = "", originalLanguage = "", originalTitle = "",
        overview = "", popularity = 0.0, posterPath = "", releaseDate = "", revenue = 0,
        runtime = 0, status = "", tagline = "", title = "", video = false, voteAverage = 0.0,
        voteCount = 0, rating = 0.0, credits = CreditsListModel(listOf(), listOf()),
        images = ImagesListModel(listOf(), listOf()), videos = VideosListModel(listOf()),
        isWishListed = false
    )

    val tvShow = TvShowDetailModel(
        id = 1, name = "Squid Game", adult = false, backdropPath = "",
        episodeRunTime = listOf(), firstAirDate = "", genres = listOf(), seasons = listOf(),
        homepage = "", inProduction = false, languages = listOf(), lastAirDate = "",
        numberOfEpisodes = 0, numberOfSeasons = 0, originCountry =  listOf(), originalLanguage = "ko",
        originalName = "오징어 게임", overview = "", popularity = 0.0, posterPath = "",
        status = "Returning Series", tagline = "Prepare for the final game.", type = "",
        voteAverage = 0.0, voteCount = 0, credits = CreditsListModel(listOf(), listOf()),
        rating = 0.0, isWishListed = false
    )

    val wishlistMovie = listOf(WishlistEntity(
        id = 0, networkId = 0, mediaType = DatabaseMediaType.Wishlist.Movie, title = "Squid Game",
        genreEntities = emptyList(), rating = 0.0, posterPath = "", isWishListed = false
    ))

    val wishlistTv = listOf(WishlistEntity(
        id = 1, networkId = 1, mediaType = DatabaseMediaType.Wishlist.TvShow, title = "Squid Game",
        genreEntities = emptyList(), rating = 0.0, posterPath = "", isWishListed = false
    ))

    @Test
    fun `getWishlist should return correct wishlist`() = runTest {
        // Arrange
        val wishlistItems = listOf(WishlistEntity(
            id = 1, networkId = 1, mediaType = DatabaseMediaType.Wishlist.Movie, title = "Squid Game",
            genreEntities = emptyList(), rating = 0.0, posterPath = "", isWishListed = false
        ))
        coEvery { wishlistDao.getByMediaType() } returns flowOf(wishlistItems)

        // Act
        val result = wishlistDatabaseSource.getWishlist().first()

        // Assert
        assertEquals(wishlistItems.size, result.size)
    }

    @Test
    fun `addMovieToWishlist should call insert with correct entity`() = runTest {
        // Arrange
        val wishlistEntity = DatabaseMediaType.Wishlist.Movie.asWishlistEntity(movie)
        coEvery { wishlistDao.insert(any())} just Runs
        coEvery { wishlistDao.getByMediaType() } returns flowOf(wishlistMovie)

        // Act
        wishlistDatabaseSource.addMovieToWishlist(movie)
        val result = wishlistDatabaseSource.getWishlist().first()

        assertEquals(DatabaseMediaType.Wishlist.Movie, result[0].mediaType)
        //verify
        coVerify{ wishlistDao.insert(wishlistEntity) }
        coVerify { wishlistDao.getByMediaType() }
    }

    @Test
    fun `addTvShowToWishlist should call insert with correct entity`() = runTest {
        // Arrange
        val wishlistEntity = DatabaseMediaType.Wishlist.TvShow.asWishlistEntity(tvShow)
        coEvery { wishlistDao.insert(any())} just Runs
        coEvery { wishlistDao.getByMediaType() } returns flowOf(wishlistTv)

        // Act
        wishlistDatabaseSource.addTvShowToWishlist(tvShow)

        val result = wishlistDatabaseSource.getWishlist().first()

        assertEquals(DatabaseMediaType.Wishlist.TvShow, result[0].mediaType)

        // Assert
        coVerify{ wishlistDao.insert(wishlistEntity) }
        coVerify { wishlistDao.getByMediaType() }
    }

    @Test
    fun `removeMovieFromWishlist should call deleteByMediaTypeAndNetworkId`() = runTest {
        coEvery{wishlistDao.deleteByMediaTypeAndNetworkId(DatabaseMediaType.Wishlist.Movie, 1)} just Runs
        // Act
        wishlistDatabaseSource.removeMovieFromWishlist(1)

        // Assert
        coVerify{wishlistDao.deleteByMediaTypeAndNetworkId(DatabaseMediaType.Wishlist.Movie, 1)}
    }

    @Test
    fun `removeTvShowFromWishlist should call deleteByMediaTypeAndNetworkId`() = runTest {
        coEvery{wishlistDao.deleteByMediaTypeAndNetworkId(DatabaseMediaType.Wishlist.TvShow, 1)} just Runs
        // Act
        wishlistDatabaseSource.removeTvShowFromWishlist(1)

        // Assert
        coVerify{wishlistDao.deleteByMediaTypeAndNetworkId(DatabaseMediaType.Wishlist.TvShow, 1)}
    }

    @Test
    fun `isMovieWishListed should return correct boolean`() = runTest {
        // Arrange
        coEvery { wishlistDao.isWishListed(DatabaseMediaType.Wishlist.Movie, 1) } returns true

        // Act
        val result = wishlistDatabaseSource.isMovieWishListed(1)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `isTvShowWishListed should return correct boolean`() = runTest {
        coEvery { wishlistDao.isWishListed(DatabaseMediaType.Wishlist.TvShow, 1) } returns true
        val result = wishlistDatabaseSource.isTvShowWishListed(1)
        assertTrue(result)
    }
}