package com.example.core.domain.usecase

import app.cash.turbine.test
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.repository.moviedetail.MovieDetailRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetDetailMovieUseCaseTest {

    private lateinit var detailRepository: MovieDetailRepository
    private lateinit var useCase: GetDetailMovieUseCase

    @Before
    fun setup() {
        detailRepository = mockk()
        useCase = GetDetailMovieUseCase(detailRepository)
    }

    @Test
    fun `should emit success when detail is returned`() = runTest {
        // Arrange
        val movieId = 1
        val expectedDetail = mockk<MovieDetailModel>()
        val expectedResponse = CinemaxResponse.Success(expectedDetail)

        coEvery { detailRepository.getDetailMovie(movieId) } returns flowOf(expectedResponse)

        // Act + Assert
        useCase(movieId).test {
            assertEquals(expectedResponse, awaitItem())
            awaitComplete()
        }

        coVerify { detailRepository.getDetailMovie(movieId) }
    }

    @Test
    fun `should emit error when repository returns error`() = runTest {
        // Arrange
        val movieId = 99
        val expectedResponse = CinemaxResponse.Failure(-1,"Something went wrong")

        coEvery { detailRepository.getDetailMovie(movieId) } returns flowOf(expectedResponse)

        // Act + Assert
        useCase(movieId).test {
            assertEquals(expectedResponse, awaitItem())
            awaitComplete()
        }

        coVerify { detailRepository.getDetailMovie(movieId) }
    }
}
