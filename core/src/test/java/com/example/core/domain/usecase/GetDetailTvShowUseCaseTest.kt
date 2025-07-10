package com.example.core.domain.usecase

import app.cash.turbine.test
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.repository.tvdetail.TvShowDetailRepository
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
class GetDetailTvShowUseCaseTest {

    private lateinit var detailRepository: TvShowDetailRepository
    private lateinit var useCase: GetDetailTvShowUseCase

    @Before
    fun setUp() {
        detailRepository = mockk()
        useCase = GetDetailTvShowUseCase(detailRepository)
    }

    @Test
    fun `should emit success when detail tv show is returned`() = runTest {
        // Arrange
        val tvShowId = 1
        val expectedDetail = mockk<TvShowDetailModel>()
        val expectedResponse = CinemaxResponse.Success(expectedDetail)

        coEvery { detailRepository.getDetailTvShow(tvShowId) } returns flowOf(expectedResponse)

        // Act + Assert
        useCase(tvShowId).test {
            assertEquals(expectedResponse, awaitItem())
            awaitComplete()
        }

        coVerify { detailRepository.getDetailTvShow(tvShowId) }
    }

    @Test
    fun `should emit error when repository returns error`() = runTest {
        // Arrange
        val tvShowId = 404
        val expectedResponse = CinemaxResponse.Failure(-1,"Not found")

        coEvery { detailRepository.getDetailTvShow(tvShowId) } returns flowOf(expectedResponse)

        // Act + Assert
        useCase(tvShowId).test {
            assertEquals(expectedResponse, awaitItem())
            awaitComplete()
        }

        coVerify { detailRepository.getDetailTvShow(tvShowId) }
    }
}
