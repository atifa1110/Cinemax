package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.repository.storage.StorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProfileUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    operator fun invoke(imageUrl : String) : Flow<CinemaxResponse<String>> {
        return storageRepository.deleteProfileFromStorage(imageUrl)
    }
}