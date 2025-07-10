package com.example.core.domain.usecase

import com.example.core.domain.repository.datastore.DataStoreRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveBoardingStateUseCaseTest {

    private lateinit var repository: DataStoreRepository
    private lateinit var useCase: SaveBoardingStateUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = SaveBoardingStateUseCase(repository)
    }

    @Test
    fun `invoke should call saveOnBoardingState with correct value`() = runTest {
        // Given
        val complete = true
        coEvery { repository.saveOnBoardingState(complete) } returns Unit

        // When
        useCase(complete)

        // Then
        coVerify(exactly = 1) { repository.saveOnBoardingState(complete) }
    }
}
