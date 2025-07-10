package com.example.core.domain.usecase

import app.cash.turbine.test
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.repository.auth.AuthRepository
import com.example.core.ui.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SaveProfileUseCaseTest {

    private val authRepository = mockk<AuthRepository>()
    private val useCase = SaveProfileUseCase(authRepository)

    private val dummyUser = User(
        userId = "123",
        name = "Atifa Fiorenza",
        email = "atifa@example.com",
        photo = "https://example.com/profile.jpg"
    )

    @Test
    fun `invoke should return Success from repository`() = runTest {
        val expectedResponse = CinemaxResponse.Success("Profile saved")

        coEvery { authRepository.saveProfile(dummyUser) } returns flowOf(expectedResponse)

        useCase(dummyUser).test {
            val result = awaitItem()
            assertEquals(expectedResponse, result)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should return Error from repository`() = runTest {
        val expectedResponse = CinemaxResponse.Failure(400,"Failed to save profile")

        coEvery { authRepository.saveProfile(dummyUser) } returns flowOf(expectedResponse)

        useCase(dummyUser).test {
            val result = awaitItem()
            assertEquals(expectedResponse, result)
            awaitComplete()
        }
    }
}
