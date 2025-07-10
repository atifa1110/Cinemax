package com.example.core.domain.usecase

import app.cash.turbine.test
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.repository.storage.StorageRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DeleteProfileUseCaseTest {

    private lateinit var repository: StorageRepository
    private lateinit var useCase: DeleteProfileUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = DeleteProfileUseCase(repository)
    }

    @Test
    fun `invoke should return success response from repository`() = runTest {
        val dummyUrl = "https://example.com/image.jpg"
        val expectedResponse = CinemaxResponse.Success("Deleted: $dummyUrl")

        coEvery {
            repository.deleteProfileFromStorage(dummyUrl)
        } returns flowOf(expectedResponse)

        useCase(dummyUrl).test {
            val result = awaitItem()
            assertEquals(expectedResponse, result)
            awaitComplete()
        }
    }
}
