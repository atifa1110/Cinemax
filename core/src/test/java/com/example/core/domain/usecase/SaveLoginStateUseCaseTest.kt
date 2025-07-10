package com.example.core.domain.usecase

import com.example.core.domain.repository.datastore.DataStoreRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveLoginStateUseCaseTest {

    private val repository = mockk<DataStoreRepository>(relaxed = true)
    private val useCase = SaveLoginStateUseCase(repository)

    @Test
    fun `invoke should call saveOnLoginState with correct value`() = runTest {
        // Given
        val complete = true

        // When
        useCase(complete)

        // Then
        coVerify { repository.saveOnLoginState(complete) }
    }
}
