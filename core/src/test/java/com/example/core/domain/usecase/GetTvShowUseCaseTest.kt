package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.TvShowModel
import com.example.core.domain.repository.tv.TvShowRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetTvShowUseCaseTest {

    private lateinit var repository: TvShowRepository
    private lateinit var useCase: GetTvShowUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTvShowUseCase(repository)
    }

    @Test
    fun `invoke returns Success with list of TvShowModel`() = runTest {
        // Given
        val expectedList = listOf(
            TvShowModel(
                id = 1, name = "Breaking Bad", overview = "Chemistry teacher turns...",
                popularity = 10.0, genres = emptyList(), originalLanguage = "en",
                voteAverage = 7.5, voteCount = 100, posterPath = "", backdropPath = "",
                rating = 3.5, firstAirDate = "", originalName = "", originCountry = emptyList(),
                seasons = 2),
            TvShowModel(id = 2, name = "The Office", overview = "Dunder Mifflin employees",
                popularity = 10.0, genres = emptyList(), originalLanguage = "en",
                voteAverage = 7.5, voteCount = 100, posterPath = "", backdropPath = "",
                rating = 3.5, firstAirDate = "", originalName = "", originCountry = emptyList(),
                seasons = 2)
        )
        val expectedResponse = CinemaxResponse.Success(expectedList)

        coEvery { repository.getByMediaType(MediaTypeModel.TvShow.Popular) } returns flowOf(expectedResponse)

        // When
        val result = useCase(MediaTypeModel.TvShow.Popular).first()

        // Then
        assertTrue(result is CinemaxResponse.Success)
        assertEquals(expectedList, result.value)
    }

    @Test
    fun `invoke returns Error when repository fails`() = runTest {
        // Given
        val errorMessage = "Network error"
        val expectedResponse = CinemaxResponse.Failure(400,errorMessage)

        coEvery { repository.getByMediaType(MediaTypeModel.TvShow.Popular) } returns flowOf(expectedResponse)

        // When
        val result = useCase(MediaTypeModel.TvShow.Popular).first()

        // Then
        assertTrue(result is CinemaxResponse.Failure)
        assertEquals(errorMessage, result.error)
    }
}
