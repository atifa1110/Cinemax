package com.example.core.data.repository.wishlist

import app.cash.turbine.test
import com.example.core.data.local.model.wishlist.WishlistEntity
import com.example.core.data.local.source.WishlistDatabaseSource
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.mapper.asWishlistModel
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.repository.utils.MainDispatcherRule
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WishListRepositoryImplTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private lateinit var databaseSource: WishlistDatabaseSource
    private lateinit var repository: WishListRepositoryImpl

    @Before
    fun setup() {
        databaseSource = mockk()
        repository = WishListRepositoryImpl(databaseSource)
    }

    @Test
    fun `getWishlist success`() = runTest {
        // Mock the database response
        val wishlistEntities = listOf(
            WishlistEntity(
                id = 0, networkId = 0, mediaType = DatabaseMediaType.Wishlist.Movie, title = "Title",
                genreEntities = emptyList(), rating = 0.0, posterPath = "", isWishListed = true
            ),
            WishlistEntity(
                id = 1, networkId = 1, mediaType = DatabaseMediaType.Wishlist.TvShow, title = "Title",
                genreEntities = emptyList(), rating = 0.0, posterPath = "", isWishListed = true
            )
        )

        val wishlistModels = wishlistEntities.map { it.asWishlistModel() }
        coEvery {databaseSource.getWishlist()} returns flowOf(wishlistEntities)

        repository.getWishlist().test {
            assertEquals(CinemaxResponse.Loading, awaitItem())  // First emission should be Loading
            assertEquals(CinemaxResponse.Success(wishlistModels), awaitItem()) // Then Success with data
            awaitComplete() // Ensure no more emissions
        }
    }

    @Test
    fun `getWishlist() should emit Loading, then Failure when exception occurs`() = runTest {
        val errorMessage = "Database error"

        // Mock an exception when fetching wishlist
        coEvery {databaseSource.getWishlist()} returns flow { throw RuntimeException(errorMessage) }

        // Collect flow emissions
        repository.getWishlist().test {
            assertEquals(CinemaxResponse.Loading, awaitItem())  // First emission should be Loading
            val failure = awaitItem()
            assertTrue(failure is CinemaxResponse.Failure && failure.error == errorMessage) // Then Failure with error message
            awaitComplete()
        }
    }

    @Test
    fun `addMovieToWishlist() should call databaseSource`() = runTest {
        val movie = MovieDetailModel(
            id = 1,adult =false, backdropPath = "", budget = 0, genres = emptyList(),
            homepage = "", imdbId = "", originalLanguage = "", originalTitle = "Title",
            overview = "", popularity = 0.0, posterPath = "", releaseDate = "", revenue = 0,
            runtime = 0, status = "", tagline = "", title = "Title", video =  false, voteAverage = 0.0,
            voteCount = 0, rating = 0.0, credits = CreditsListModel(emptyList(), emptyList()),
            images = ImagesListModel(emptyList(), emptyList()), videos =  VideosListModel(emptyList()),
            isWishListed = true
        )
        coEvery{databaseSource.addMovieToWishlist(movie)} just Runs
        repository.addMovieToWishlist(movie)
        coVerify{databaseSource.addMovieToWishlist(movie)}
    }

    @Test
    fun `addTvShowToWishlist() should call databaseSource`() = runTest {
        val tvShow = TvShowDetailModel(
            id = 1, name = "Title", adult = false, backdropPath = "", episodeRunTime = emptyList(),
            firstAirDate = "", genres =  emptyList(), seasons = emptyList(), homepage = "",
            inProduction = false, languages = emptyList(), lastAirDate = "", numberOfEpisodes = 0,
            numberOfSeasons = 0, originCountry = emptyList(), originalLanguage = "", originalName = "",
            overview = "", popularity = 0.0, posterPath = "", status = "", tagline = "",
            type = "", voteAverage = 0.0, voteCount = 0, credits = CreditsListModel(emptyList(), emptyList()),
            rating = 0.0, isWishListed = false
        )

        coEvery {databaseSource.addTvShowToWishlist(tvShow)} just Runs
        repository.addTvShowToWishlist(tvShow)
        coVerify{databaseSource.addTvShowToWishlist(tvShow)}
    }

    @Test
    fun `removeMovieFromWishlist() should call databaseSource`() = runTest {
        val movie = MovieDetailModel(
            id = 1,adult =false, backdropPath = "", budget = 0, genres = emptyList(),
            homepage = "", imdbId = "", originalLanguage = "", originalTitle = "Title",
            overview = "", popularity = 0.0, posterPath = "", releaseDate = "", revenue = 0,
            runtime = 0, status = "", tagline = "", title = "Title", video =  false, voteAverage = 0.0,
            voteCount = 0, rating = 0.0, credits = CreditsListModel(emptyList(), emptyList()),
            images = ImagesListModel(emptyList(), emptyList()), videos =  VideosListModel(emptyList()),
            isWishListed = true
        )
        coEvery{databaseSource.addMovieToWishlist(movie)} just Runs
        coEvery{databaseSource.removeMovieFromWishlist(movie.id)} just Runs

        repository.addMovieToWishlist(movie)
        repository.removeMovieFromWishlist(movie.id)

        coVerify{databaseSource.addMovieToWishlist(movie)}
        coVerify{databaseSource.removeMovieFromWishlist(movie.id)}
    }

    @Test
    fun `removeTvShowFromWishlist() should call databaseSource`() = runTest {
        val tvShow = TvShowDetailModel(
            id = 1, name = "Title", adult = false, backdropPath = "", episodeRunTime = emptyList(),
            firstAirDate = "", genres =  emptyList(), seasons = emptyList(), homepage = "",
            inProduction = false, languages = emptyList(), lastAirDate = "", numberOfEpisodes = 0,
            numberOfSeasons = 0, originCountry = emptyList(), originalLanguage = "", originalName = "",
            overview = "", popularity = 0.0, posterPath = "", status = "", tagline = "",
            type = "", voteAverage = 0.0, voteCount = 0, credits = CreditsListModel(emptyList(), emptyList()),
            rating = 0.0, isWishListed = false
        )

        coEvery { databaseSource.addTvShowToWishlist(tvShow) } just Runs
        coEvery { databaseSource.removeTvShowFromWishlist(tvShow.id) } just Runs

        repository.addTvShowToWishlist(tvShow)
        repository.removeTvShowFromWishlist(tvShow.id)

        coVerify{ databaseSource.addTvShowToWishlist(tvShow) }
        coVerify { databaseSource.removeTvShowFromWishlist(tvShow.id)}
    }
}