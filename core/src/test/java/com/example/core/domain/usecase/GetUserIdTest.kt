package com.example.core.domain.usecase

import com.example.core.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserIdTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var getUserId: GetUserId

    @Before
    fun setup() {
        authRepository = mockk()
        getUserId = GetUserId(authRepository)
    }

    @Test
    fun `invoke returns FirebaseUser when user is logged in`() {
        // Given
        val mockFirebaseUser = mockk<FirebaseUser>()
        every { authRepository.getUserId() } returns mockFirebaseUser

        // When
        val result = getUserId()

        // Then
        assertNotNull(result)
        assertEquals(mockFirebaseUser, result)
    }

    @Test
    fun `invoke returns null when user is not logged in`() {
        // Given
        every { authRepository.getUserId() } returns null

        // When
        val result = getUserId()

        // Then
        assertNull(result)
    }
}
