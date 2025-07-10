package com.example.core.data.repository.moviedetail

import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.detailMovie.ImagesListEntity
import com.example.core.data.local.model.detailMovie.MovieDetailsEntity
import com.example.core.data.local.model.detailMovie.VideosListEntity
import com.example.core.data.local.source.MovieDetailsDatabaseSource
import com.example.core.data.local.source.WishlistDatabaseSource
import com.example.core.data.network.datasource.MovieNetworkDataSource
import com.example.core.data.network.model.movie.MovieDetailNetwork
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.ImagesListModel
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.model.VideosListModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MovieDetailRepositoryImplTest {

    // Mock dependencies
    private lateinit var databaseDataSource: MovieDetailsDatabaseSource
    private lateinit var networkDataSource: MovieNetworkDataSource
    private lateinit var wishlistDatabaseSource: WishlistDatabaseSource

    // Repository under test
    private lateinit var repository: MovieDetailRepositoryImpl

    @Before
    fun setUp() {
        databaseDataSource = mockk()
        networkDataSource = mockk()
        wishlistDatabaseSource = mockk()
        repository = MovieDetailRepositoryImpl(
            databaseDataSource,
            networkDataSource,
            wishlistDatabaseSource
        )
    }

    @Test
    fun `getDetailMovie should emit loading, fetch from network, save to DB, and return success`() = runTest {
        val movieId = 1
        val mockMovieEntity = MovieDetailsEntity(
            id = movieId, adult = false, backdropPath = "", budget = 0, genreEntities = listOf(),
            homepage = "", imdbId = "tt0241527", originalLanguage = "", originalTitle =   "Test Movie",
            overview = "", popularity = 0.0, posterPath = "", releaseDate = "", revenue = 0,
            runtime = 0, status = "", tagline = "", title =   "Test Movie",
            video = false, voteAverage = 0.0, voteCount = 0, rating = 0.0,
            credits = CreditsListEntity(listOf(), listOf()),
            images = ImagesListEntity(listOf(), listOf()), videos = VideosListEntity(listOf()),
        )
        val mockMovieDetail = MovieDetailModel(
            id = movieId, adult = false, backdropPath = "", budget = 0, genres = listOf(),
            homepage = "", imdbId = "tt0241527", originalLanguage = "", originalTitle = "Test Movie",
            overview = "", popularity = 0.0, posterPath = "", releaseDate = "", revenue = 0,
            runtime = 0, status = "", tagline = "", title =   "Test Movie", video = false,
            voteAverage = 0.0, voteCount = 0, rating = 0.0, credits = CreditsListModel(listOf(), listOf()),
            images = ImagesListModel(listOf(), listOf()), videos = VideosListModel(listOf()),
            isWishListed = false
        )

        val mockApiResponse = MovieDetailNetwork(
            id = movieId,
            adult = false,
            title = "Test Movie",
            overview = "This is a sample overview",
        )

        // Mock database and network calls
        coEvery { databaseDataSource.getById(movieId) } returns flowOf(mockMovieEntity)
        coEvery { networkDataSource.getDetailMovie(movieId) } returns CinemaxResponse.Success(mockApiResponse)
        coEvery { wishlistDatabaseSource.isMovieWishListed(movieId) } returns false
        coEvery { databaseDataSource.deleteAndInsert(any()) } just Runs

        val results = repository.getDetailMovie(movieId).toList()
        // Assert
        assertEquals(3, results.size) // Loading, Loading, Success

        assertTrue(results[0] is CinemaxResponse.Loading)
        assertTrue(results[1] is CinemaxResponse.Loading)
        assertTrue(results[2] is CinemaxResponse.Success)

        val successResult = results[2] as CinemaxResponse.Success
        assertEquals(mockMovieDetail, successResult.value) // Check if the final data is correct

        // Verify that the fetched movie details were saved to the database
        coVerify { databaseDataSource.deleteAndInsert(any()) }
    }

    @Test
    fun `getDetailMovie should emit failure when network fetch fails`() = runTest {
        val movieId = 1
        val errorMessage = "Network Error"

        // Mock DB returning null and network throwing an error
        coEvery { databaseDataSource.getById(movieId) } returns flowOf(null)
        coEvery { networkDataSource.getDetailMovie(movieId)
        } returns CinemaxResponse.Failure(400,"Network Error")

        val results = repository.getDetailMovie(movieId).toList()
        assertTrue(results[2] is CinemaxResponse.Failure)

        val failureResult = results[2] as CinemaxResponse.Failure
        assertEquals(errorMessage, failureResult.error)
    }
}