package com.example.core.domain.repository.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveOnLoginState(completed : Boolean)
    suspend fun saveOnBoardingState(completed : Boolean)
    fun onLoginState() : Flow<Boolean>
    fun onBoardingState(): Flow<Boolean>
}