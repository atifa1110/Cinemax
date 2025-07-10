package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.repository.auth.AuthRepository
import com.example.core.ui.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetProfileUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var getProfileUseCase: GetProfileUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        getProfileUseCase = GetProfileUseCase(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit Success when repository returns valid user`() = runTest {
        // Given
        val dummyUser = User(
            userId = "1",
            name = "Atifa Fiorenza",
            email = "atifa@example.com",
            photo = "https://example.com/profile.jpg"
        )
        val expectedResponse = CinemaxResponse.Success(dummyUser)

        coEvery { authRepository.getProfile() } returns flowOf(expectedResponse)

        // When
        val result = getProfileUseCase().first()

        // Then
        assertTrue(result is CinemaxResponse.Success)
        assertEquals(dummyUser, result.value)
    }

    @Test
    fun `should emit Error when repository returns error`() = runTest {
        // Given
        val errorResponse = CinemaxResponse.Failure(-1,"Unauthorized")

        coEvery { authRepository.getProfile() } returns flowOf(errorResponse)

        // When
        val result = getProfileUseCase().first()

        // Then
        assertTrue(result is CinemaxResponse.Failure)
        assertEquals("Unauthorized", result.error)
    }
}
