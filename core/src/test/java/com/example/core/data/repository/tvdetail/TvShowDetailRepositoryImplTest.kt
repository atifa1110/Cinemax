package com.example.core.data.repository.tvdetail

import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.tv.TvShowDetailsEntity
import com.example.core.data.local.source.TvShowDetailsDatabaseSource
import com.example.core.data.local.source.WishlistDatabaseSource
import com.example.core.data.network.datasource.TvShowNetworkDataSource
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.TvShowDetailModel
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

class TvShowDetailRepositoryImplTest {

    // Mock dependencies
    private lateinit var databaseDataSource: TvShowDetailsDatabaseSource
    private lateinit var networkDataSource: TvShowNetworkDataSource
    private lateinit var wishlistDatabaseSource: WishlistDatabaseSource

    // Repository under test
    private lateinit var repository: TvShowDetailRepositoryImpl

    @Before
    fun setUp() {
        databaseDataSource = mockk()
        networkDataSource = mockk()
        wishlistDatabaseSource = mockk()
        repository = TvShowDetailRepositoryImpl(
            databaseDataSource,
            networkDataSource,
            wishlistDatabaseSource
        )
    }

    @Test
    fun `getDetailTvShow should emit loading, fetch from network, save to DB, and return success`() = runTest {
        val tvId = 1
        val mockTvEntity = TvShowDetailsEntity(
            id = tvId, adult = false, backdropPath = "", name = "TvShow", episodeRunTime = emptyList(), firstAirDate = "",
            genres = emptyList(), seasons = emptyList(), homepage = "", inProduction = false, languages = emptyList(), lastAirDate = "",
            numberOfEpisodes = 0, numberOfSeasons = 0, originCountry = emptyList(), originalLanguage = "", originalName = "",
            overview = "This is a sample overview", popularity = 0.0, posterPath = "", status = "", tagline = "", type = "", voteAverage = 0.0,
            voteCount = 0,  rating = 0.0, credits = CreditsListEntity(listOf(), listOf())
        )
        val mockTvDetail = TvShowDetailModel(
            id = tvId, adult = false, backdropPath = "", name = "TvShow", episodeRunTime = emptyList(), firstAirDate = "",
            genres = emptyList(), seasons = emptyList(), homepage = "", inProduction = false, languages = emptyList(), lastAirDate = "",
            numberOfEpisodes = 0, numberOfSeasons = 0, originCountry = emptyList(), originalLanguage = "", originalName = "",
            overview = "This is a sample overview", popularity = 0.0, posterPath = "", status = "", tagline = "", type = "", voteAverage = 0.0,
            voteCount = 0, credits = CreditsListModel(listOf(), listOf()), rating = 0.0, isWishListed = false
        )

        val mockApiResponse = TvShowDetailNetwork(
            id = tvId,
            name = "TvShow",
            adult = false,
            overview = "This is a sample overview",
        )

        // Mock database and network calls
        coEvery { databaseDataSource.getById(tvId) } returns flowOf(mockTvEntity)
        coEvery { networkDataSource.getDetailTv(tvId) } returns CinemaxResponse.Success(mockApiResponse)
        coEvery { wishlistDatabaseSource.isTvShowWishListed(tvId) } returns false
        coEvery { databaseDataSource.deleteAndInsert(any()) } just Runs

        val results = repository.getDetailTvShow(tvId).toList()
        // Assert
        assertEquals(3, results.size) // Loading, Loading, Success

        assertTrue(results[0] is CinemaxResponse.Loading)
        assertTrue(results[1] is CinemaxResponse.Loading)
        assertTrue(results[2] is CinemaxResponse.Success)

        val successResult = results[2] as CinemaxResponse.Success
        assertEquals(mockTvDetail, successResult.value) // Check if the final data is correct

        // Verify that the fetched movie details were saved to the database
        coVerify{ databaseDataSource.deleteAndInsert(any()) }
    }

    @Test
    fun `getDetailMovie should emit failure when network fetch fails`() = runTest {
        val tvId = 1
        val errorMessage = "Network Error"

        // Mock DB returning null and network throwing an error
        coEvery { databaseDataSource.getById(tvId) } returns flowOf(null)
        coEvery { networkDataSource.getDetailTv(tvId) } returns CinemaxResponse.Failure(400,"Network Error")

        val results = repository.getDetailTvShow(tvId).toList()

        assertTrue(results[2] is CinemaxResponse.Failure)

        val failureResult = results[2] as CinemaxResponse.Failure
        assertEquals(errorMessage, failureResult.error)
    }
}