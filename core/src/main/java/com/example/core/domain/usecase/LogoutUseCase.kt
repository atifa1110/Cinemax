package com.example.core.domain.usecase

import com.example.core.domain.repository.auth.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() {
        return authRepository.logout()
    }
}