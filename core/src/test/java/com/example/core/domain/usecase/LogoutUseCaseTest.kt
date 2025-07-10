package com.example.core.domain.usecase

import com.example.core.domain.repository.auth.AuthRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var logoutUseCase: LogoutUseCase

    @Before
    fun setUp() {
        authRepository = mockk(relaxed = true)
        logoutUseCase = LogoutUseCase(authRepository)
    }

    @Test
    fun `invoke calls logout on authRepository`() {
        // When
        logoutUseCase()

        // Then
        verify(exactly = 1) { authRepository.logout() }
    }
}
