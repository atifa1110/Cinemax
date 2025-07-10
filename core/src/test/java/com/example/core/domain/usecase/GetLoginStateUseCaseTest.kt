package com.example.core.domain.usecase

import app.cash.turbine.test
import com.example.core.domain.repository.datastore.DataStoreRepository
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
class GetLoginStateUseCaseTest {

    private lateinit var repository: DataStoreRepository
    private lateinit var useCase: GetLoginStateUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetLoginStateUseCase(repository)
    }

    @Test
    fun `should emit true when user is logged in`() = runTest {
        // Arrange
        coEvery { repository.onLoginState() } returns flowOf(true)

        // Act + Assert
        useCase().test {
            assertEquals(true, awaitItem())
            awaitComplete()
        }

        coVerify { repository.onLoginState() }
    }

    @Test
    fun `should emit false when user is logged out`() = runTest {
        // Arrange
        coEvery { repository.onLoginState() } returns flowOf(false)

        // Act + Assert
        useCase().test {
            assertEquals(false, awaitItem())
            awaitComplete()
        }

        coVerify { repository.onLoginState() }
    }
}
