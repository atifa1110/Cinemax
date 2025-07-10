package com.example.core.domain.usecase

import com.example.core.domain.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginStateUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() : Flow<Boolean>{
        return dataStoreRepository.onLoginState()
    }
}