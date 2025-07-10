package com.example.core.data.local.source

import com.example.core.data.local.dao.movie.MovieDetailsDao
import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.detailMovie.ImagesListEntity
import com.example.core.data.local.model.detailMovie.MovieDetailsEntity
import com.example.core.data.local.model.detailMovie.VideosListEntity
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

class MovieDetailsDatabaseSourceTest {

    private lateinit var movieDetailsDao: MovieDetailsDao
    private lateinit var transactionProvider: FakeTransactionProvider
    private lateinit var source: MovieDetailsDatabaseSource

    @Before
    fun setUp() {
        movieDetailsDao = mockk(relaxed = true)
        transactionProvider = mockk()

        // Initialize class under test
        source = MovieDetailsDatabaseSource(
            movieDetailsDao = movieDetailsDao,
            transactionProvider = transactionProvider
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    val movieDetails = MovieDetailsEntity(
        id = 1,
        adult = false,
        backdropPath = "",
        budget = 0,
        genreEntities = listOf(),
        homepage = "",
        imdbId = "tt0241527",
        originalLanguage = "",
        originalTitle =  "Harry Potter and the Philosopher's Stone",
        overview = "",
        popularity = 0.0,
        posterPath = "",
        releaseDate = "",
        revenue = 0,
        runtime = 0,
        status = "",
        tagline = "",
        title =  "Harry Potter and the Philosopher's Stone",
        video = false,
        voteAverage = 0.0,
        voteCount = 0,
        rating = 0.0,
        credits = CreditsListEntity(listOf(), listOf()),
        images = ImagesListEntity(listOf(), listOf()),
        videos = VideosListEntity(listOf())
    )

    @Test
    fun `getById should return correct movie details`() = runTest {
        // Arrange
        coEvery { movieDetailsDao.getById(1) } returns flowOf(movieDetails)
        // Act
        val result = source.getById(1).first()
        // Assert
        assertEquals(movieDetails, result)
    }

    @Test
    fun `deleteAndInsert should delete and insert correctly`() = runTest {

        val transactionSlot = slot<suspend () -> Unit>()

        coEvery { transactionProvider.runWithTransaction(capture(transactionSlot)) } just Runs
        coEvery { movieDetailsDao.deleteById(movieDetails.id) } just Runs
        coEvery { movieDetailsDao.insert(movieDetails) } just Runs

        // When
        source.deleteAndInsert(movieDetails)
        transactionSlot.captured.invoke()

        // Then
        coVerifyOrder {
            movieDetailsDao.deleteById(movieDetails.id)
            movieDetailsDao.insert(movieDetails)
        }
    }
}