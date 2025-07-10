package com.example.core.domain.usecase

import com.example.core.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class SaveBoardingStateUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(complete : Boolean){
        return dataStoreRepository.saveOnBoardingState(complete)
    }
}