package com.example.core.domain.usecase

import app.cash.turbine.test
import com.example.core.domain.repository.datastore.DataStoreRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetBoardStateUseCaseTest {

    private lateinit var dataStoreRepository: DataStoreRepository
    private lateinit var useCase: GetBoardStateUseCase

    @Before
    fun setup() {
        dataStoreRepository = mockk()
        useCase = GetBoardStateUseCase(dataStoreRepository)
    }

    @Test
    fun `should emit correct onboarding state`() = runTest {
        // Arrange
        val expectedState = true
        every { dataStoreRepository.onBoardingState() } returns flowOf(expectedState)

        // Act & Assert
        useCase().test {
            assertEquals(expectedState, awaitItem())
            awaitComplete()
        }

        verify { dataStoreRepository.onBoardingState() }
    }
}
