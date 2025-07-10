package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.SearchModel
import com.example.core.domain.repository.search.SearchHistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetSearchHistoryUseCaseTest {

    private lateinit var repository: SearchHistoryRepository
    private lateinit var useCase: GetSearchHistoryUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        useCase = GetSearchHistoryUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit Success with search history`() = runTest {
        // Given
        val mockSearchHistory = listOf(
            SearchModel(
                id = 1, title = "Inception", overview = "A dream within a dream",
                popularity = 90.0, releaseDate = "", rating = 4.0, runtime = 120,
                originalTitle = "", originalLanguage = "", genres = emptyList(),
                voteAverage = 0.0, voteCount = 2, posterPath = "", profilePath = "",
                backdropPath = "", video = false, mediaType = "movie", timestamp = 0
            ),
            SearchModel(id = 2, title = "Interstellar", overview = "Time and space",
                popularity = 90.0, releaseDate = "", rating = 4.0, runtime = 120,
                originalTitle = "", originalLanguage = "", genres = emptyList(),
                voteAverage = 0.0, voteCount = 2, posterPath = "", profilePath = "",
                backdropPath = "", video = false, mediaType = "movie", timestamp = 0
            )
        )
        val expected = CinemaxResponse.Success(mockSearchHistory)

        coEvery { repository.getSearchHistory() } returns flowOf(expected)

        // When
        val result = useCase().first()

        // Then
        assertTrue(result is CinemaxResponse.Success)
        assertEquals(mockSearchHistory, result.value)
    }

    @Test
    fun `should emit Error when repository fails`() = runTest {
        // Given
        val expected = CinemaxResponse.Failure(-1,"Database unavailable")
        coEvery { repository.getSearchHistory() } returns flowOf(expected)

        // When
        val result = useCase().first()

        // Then
        assertTrue(result is CinemaxResponse.Failure)
        assertEquals("Database unavailable", result.error)
    }
}

