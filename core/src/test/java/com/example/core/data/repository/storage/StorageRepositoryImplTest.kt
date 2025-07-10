package com.example.core.data.repository.storage

import android.net.Uri
import com.example.core.data.network.datasource.AuthNetworkDataSource
import com.example.core.data.network.response.CinemaxResponse
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class StorageRepositoryImplTest {

    private lateinit var authNetworkDataSource: AuthNetworkDataSource
    private lateinit var repository: StorageRepositoryImpl

    // ✅ Use TestCoroutineDispatcher for Coroutine Handling
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        authNetworkDataSource = mock()
        repository = StorageRepositoryImpl(authNetworkDataSource)
    }

    @Test
    fun `uploadImageUri should emit success with download URL on successful upload and download URL retrieval`() = runTest {
        // Arrange
        val imageUri = mock<Uri>() // Mocked input Uri
        val mockStorageReference = mock<StorageReference>()
        val mockUploadTask = mock<UploadTask>()
        val mockDownloadUrlTask = mock<Task<Uri>>()

        val imageUrl = "https://example.com/image.jpg"
        val downloadUri = mock<Uri>() // Avoid calling Uri.parse()
        whenever(downloadUri.toString()).thenReturn(imageUrl)

        whenever(authNetworkDataSource.uploadStorage()).thenReturn(mockStorageReference)
        whenever(mockStorageReference.putFile(imageUri)).thenReturn(mockUploadTask)

        // Simulate upload success
        whenever(mockUploadTask.addOnSuccessListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnSuccessListener<Void>
            listener.onSuccess(null)
            mockUploadTask
        }
        whenever(mockUploadTask.addOnFailureListener(any())).thenReturn(mockUploadTask)

        // Simulate download URL retrieval success
        whenever(mockStorageReference.downloadUrl).thenReturn(mockDownloadUrlTask)
        whenever(mockDownloadUrlTask.addOnSuccessListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnSuccessListener<Uri>
            listener.onSuccess(downloadUri)
            mockDownloadUrlTask
        }
        whenever(mockDownloadUrlTask.addOnFailureListener(any())).thenReturn(mockDownloadUrlTask)

        // Act
        val result = repository.uploadImageUri(imageUri).toList()

        // Assert
        assertEquals(CinemaxResponse.Loading, result[0])
        assertEquals(CinemaxResponse.Success(imageUrl), result[1])
    }

    @Test
    fun `uploadImageUri should emit failure on upload failure`() = runTest {
        // Arrange
        val imageUri = mock<Uri>()
        val mockStorageReference = mock<StorageReference>()
        val mockUploadTask = mock<UploadTask>()
        val errorMessage = "Image upload failed"

        whenever(authNetworkDataSource.uploadStorage()).thenReturn(mockStorageReference)
        whenever(mockStorageReference.putFile(imageUri)).thenReturn(mockUploadTask)
        whenever(mockUploadTask.addOnFailureListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnFailureListener
            listener.onFailure(Exception(errorMessage))
            mockUploadTask
        }
        whenever(mockUploadTask.addOnSuccessListener(any())).thenReturn(mockUploadTask)

        // Act
        val result = repository.uploadImageUri(imageUri).toList()

        // Assert
        assertEquals(CinemaxResponse.Loading, result[0])
        assertEquals(CinemaxResponse.Failure(-1,errorMessage),result[1])
    }

    @Test
    fun `uploadImageUri should emit failure on download URL retrieval failure`() = runTest {
        // Arrange
        val imageUri = mock<Uri>()
        val mockStorageReference = mock<StorageReference>()
        val mockUploadTask = mock<UploadTask>()
        val mockDownloadUrlTask = mock<Task<Uri>>()
        val errorMessage = "Failed to get download URL"

        whenever(authNetworkDataSource.uploadStorage()).thenReturn(mockStorageReference)
        whenever(mockStorageReference.putFile(imageUri)).thenReturn(mockUploadTask)
        whenever(mockUploadTask.addOnSuccessListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnSuccessListener<Void>
            listener.onSuccess(null)
            mockUploadTask
        }
        whenever(mockUploadTask.addOnFailureListener(any())).thenReturn(mockUploadTask)

        whenever(mockStorageReference.downloadUrl).thenReturn(mockDownloadUrlTask)
        whenever(mockDownloadUrlTask.addOnFailureListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnFailureListener
            listener.onFailure(Exception(errorMessage))
            mockDownloadUrlTask
        }
        whenever(mockDownloadUrlTask.addOnSuccessListener(any())).thenReturn(mockDownloadUrlTask)

        // Act
        val result = repository.uploadImageUri(imageUri).toList()

        // Assert
        assertEquals(CinemaxResponse.Loading, result[0])
        assertEquals(CinemaxResponse.Failure(-1,errorMessage),result[1])
    }

    @Test
    fun `removeProfileImage should emit success on successful removal`() = runTest {
        // Arrange
        val mockDocumentReference = mock<DocumentReference>()
        val mockUpdateTask = mock<Task<Void>>()

        whenever(authNetworkDataSource.removeProfileDatabase()).thenReturn(mockDocumentReference)
        whenever(mockDocumentReference.update(any<Map<String, Any>>())).thenReturn(mockUpdateTask)
        whenever(mockUpdateTask.addOnSuccessListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnSuccessListener<Void>
            listener.onSuccess(null)
            mockUpdateTask
        }
        whenever(mockUpdateTask.addOnFailureListener(any())).thenReturn(mockUpdateTask)

        // Act
        val result = repository.removeProfileImage().toList()

        // Assert
        assertEquals(listOf(CinemaxResponse.Success("Image URL Removed Successfully from Firestore")), result)
    }

    @Test
    fun `removeProfileImage should emit failure on removal failure`() = runTest {
        // Arrange
        val mockDocumentReference = mock<DocumentReference>()
        val mockUpdateTask = mock<Task<Void>>()
        val errorMessage = "Failed to Remove Image URL"

        whenever(authNetworkDataSource.removeProfileDatabase()).thenReturn(mockDocumentReference)
        whenever(mockDocumentReference.update(any<Map<String, Any>>())).thenReturn(mockUpdateTask)
        whenever(mockUpdateTask.addOnFailureListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnFailureListener
            listener.onFailure(Exception(errorMessage))
            mockUpdateTask
        }
        whenever(mockUpdateTask.addOnSuccessListener(any())).thenReturn(mockUpdateTask)

        // Act
        val result = repository.removeProfileImage().toList()

        // Assert
        assertEquals(listOf(CinemaxResponse.Failure(-1,errorMessage)), result)
    }

}
