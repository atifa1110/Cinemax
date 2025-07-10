package com.example.core.domain.usecase


import app.cash.turbine.test
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.repository.auth.AuthRepository
import com.example.core.ui.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class RegisterWithEmailAndPasswordUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var registerUseCase: RegisterWithEmailAndPasswordUseCase

    @Before
    fun setUp() {
        authRepository = mockk()
        registerUseCase = RegisterWithEmailAndPasswordUseCase(authRepository)
    }

    @Test
    fun `invoke should return success response from repository`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "securePassword123"
        val user = User(
            userId = "uid123",
            name = "Test User",
            email = email,
            photo = "https://example.com/profile.jpg"
        )
        val expectedResponse = CinemaxResponse.Success("User registered successfully")

        coEvery {
            authRepository.registerWithEmailAndPassword(email, password, user)
        } returns flowOf(expectedResponse)

        // When & Then
        registerUseCase(email, password, user).test {
            assertEquals(expectedResponse, awaitItem())
            awaitComplete()
        }
    }
}
