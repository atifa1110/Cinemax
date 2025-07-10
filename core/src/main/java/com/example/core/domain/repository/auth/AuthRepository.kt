package com.example.core.domain.repository.auth

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.ui.model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getUserId() : FirebaseUser?

    fun signInWithEmailAndPassword(email: String, password: String) : Flow<CinemaxResponse<String>>

    fun registerFirebaseUser(email: String, password: String, user: User): Flow<CinemaxResponse<User>>

    fun saveUserData(user: User): Flow<CinemaxResponse<String>>

    fun registerWithEmailAndPassword(email: String, password: String, user : User) : Flow<CinemaxResponse<String>>

    fun logout()
    fun getProfile() : Flow<CinemaxResponse<User>>
    fun saveProfile(user: User) : Flow<CinemaxResponse<String>>
}