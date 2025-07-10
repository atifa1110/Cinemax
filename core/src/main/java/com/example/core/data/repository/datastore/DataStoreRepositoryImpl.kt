package com.example.core.data.repository.datastore

import com.example.core.data.local.datastore.DataStorePreference
import com.example.core.domain.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference
) : DataStoreRepository{

    override suspend fun saveOnLoginState(completed: Boolean) {
        dataStorePreference.saveOnLoginState(completed)
    }

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStorePreference.saveOnBoardingState(completed)
    }

    override fun onLoginState(): Flow<Boolean> {
        return dataStorePreference.onLoginState()
    }

    override fun onBoardingState(): Flow<Boolean> {
        return dataStorePreference.onBoardingState()
    }

}