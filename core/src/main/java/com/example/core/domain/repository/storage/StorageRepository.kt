package com.example.core.domain.repository.storage

import android.net.Uri
import com.example.core.data.network.response.CinemaxResponse
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    fun uploadImageUri(imageUri: Uri): Flow<CinemaxResponse<String>>

    fun removeProfileImage(): Flow<CinemaxResponse<String>>

    fun deleteProfileFromStorage(imageUrl: String) : Flow<CinemaxResponse<String>>
}