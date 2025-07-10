package com.example.core.domain.usecase

import com.example.core.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetUserId @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): FirebaseUser? {
        return authRepository.getUserId()
    }
}