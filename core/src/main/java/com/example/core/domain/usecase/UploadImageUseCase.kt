package com.example.core.domain.usecase

import android.net.Uri
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.repository.storage.StorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    operator fun invoke(imageUri : Uri) : Flow<CinemaxResponse<String>> {
        return storageRepository.uploadImageUri(imageUri)
    }
}