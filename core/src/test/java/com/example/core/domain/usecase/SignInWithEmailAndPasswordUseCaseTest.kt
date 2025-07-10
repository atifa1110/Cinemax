package com.example.core.domain.usecase

import app.cash.turbine.test
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.repository.auth.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SignInWithEmailAndPasswordUseCaseTest {

    private val repository = mockk<AuthRepository>()
    private val useCase = SignInWithEmailAndPasswordUseCase(repository)

    @Test
    fun `invoke should return success response from repository`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val expectedResponse = CinemaxResponse.Success("Login successful")

        coEvery {
            repository.signInWithEmailAndPassword(email, password)
        } returns flowOf(expectedResponse)

        // When + Then
        useCase(email, password).test {
            val actual = awaitItem()
            assertEquals(expectedResponse, actual)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should return error response from repository`() = runTest {
        // Given
        val email = "wrong@example.com"
        val password = "wrongpass"
        val expectedResponse = CinemaxResponse.Failure(400,"Invalid credentials")

        coEvery {
            repository.signInWithEmailAndPassword(email, password)
        } returns flowOf(expectedResponse)

        // When + Then
        useCase(email, password).test {
            val actual = awaitItem()
            assertEquals(expectedResponse, actual)
            awaitComplete()
        }
    }
}
