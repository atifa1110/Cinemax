package com.example.core.domain.usecase

import android.net.Uri
import app.cash.turbine.test
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.repository.storage.StorageRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UploadImageUseCaseTest {

    private val repository = mockk<StorageRepository>()
    private val useCase = UploadImageUseCase(repository)

    @Test
    fun `invoke should return Success from repository`() = runTest {
        // Given
        val uri = mockk<Uri>()
        val expectedUrl = "https://firebase.storage/download/success.jpg"
        val expectedResponse = CinemaxResponse.Success(expectedUrl)

        coEvery { repository.uploadImageUri(uri) } returns flowOf(expectedResponse)

        // When + Then
        useCase(uri).test {
            val result = awaitItem()
            assertEquals(expectedResponse, result)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should return Error from repository`() = runTest {
        // Given
        val uri = mockk<Uri>()
        val expectedError = CinemaxResponse.Failure(400,"Upload failed")

        coEvery { repository.uploadImageUri(uri) } returns flowOf(expectedError)

        // When + Then
        useCase(uri).test {
            val result = awaitItem()
            assertEquals(expectedError, result)
            awaitComplete()
        }
    }
}
