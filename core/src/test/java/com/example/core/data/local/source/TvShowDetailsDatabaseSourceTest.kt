package com.example.core.data.local.source

import com.example.core.data.local.dao.tv.TvShowDetailsDao
import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.tv.TvShowDetailsEntity
import com.example.core.data.repository.utils.FakeTransactionProvider
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class TvShowDetailsDatabaseSourceTest {
    private lateinit var tvDetailsDao: TvShowDetailsDao
    private lateinit var transactionProvider: FakeTransactionProvider
    private lateinit var source: TvShowDetailsDatabaseSource

    @Before
    fun setUp() {
        tvDetailsDao = mockk(relaxed = true)
        transactionProvider = mockk()

        // Initialize class under test
        source = TvShowDetailsDatabaseSource(tvDetailsDao,transactionProvider)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    val tvShowDetails = TvShowDetailsEntity(
        id = 1, name = "Squid Game", adult = false, backdropPath = "",
        episodeRunTime = listOf(), firstAirDate = "", genres = listOf(),
        seasons =  listOf(), homepage = "", inProduction = false,
        languages = listOf(), lastAirDate = "", numberOfEpisodes = 0,
        numberOfSeasons = 0, originCountry = listOf(), originalLanguage = "",
        originalName = "오징어 게임", overview = "", popularity = 0.0,
        posterPath = "", status =  "Returning Series", tagline = "Prepare for the final game.",
        type = "", voteAverage = 0.0, voteCount = 0, credits = CreditsListEntity(listOf(), listOf()),
        rating = 0.0
    )

    @Test
    fun `getById should return correct movie details`() = runTest {
        // Arrange
        coEvery { tvDetailsDao.getById(1) } returns flowOf(tvShowDetails)
        // Act
        val result = source.getById(1).first()
        // Assert
        assertEquals(tvShowDetails, result)
    }

    @Test
    fun `deleteAndInsert should delete and insert correctly`() = runTest {

        val transactionSlot = slot<suspend () -> Unit>()

        coEvery { transactionProvider.runWithTransaction(capture(transactionSlot)) } just Runs
        coEvery { tvDetailsDao.deleteById(tvShowDetails.id) } just Runs
        coEvery { tvDetailsDao.insert(tvShowDetails) } just Runs

        // When
        source.deleteAndInsert(tvShowDetails)
        transactionSlot.captured.invoke()

        // Then
        coVerifyOrder {
            tvDetailsDao.deleteById(tvShowDetails.id)
            tvDetailsDao.insert(tvShowDetails)
        }
    }

}