package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.repository.auth.AuthRepository
import com.example.core.ui.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(user: User) : Flow<CinemaxResponse<String>> {
        return authRepository.saveProfile(user)
    }
}