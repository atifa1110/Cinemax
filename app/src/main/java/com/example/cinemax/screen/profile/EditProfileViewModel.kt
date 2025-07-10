package com.example.cinemax.screen.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.usecase.DeleteProfileUseCase
import com.example.core.domain.usecase.GetProfileUseCase
import com.example.core.domain.usecase.SaveProfileUseCase
import com.example.core.domain.usecase.UploadImageUseCase
import com.example.core.ui.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileUiState(
    val isSheetOpen : Boolean = false,
    val userName : String? = "",
    val name : String? = "",
    val nameError : String? = null,
    val email : String? = "",
    val phoneNumber : String? = "",
    val phoneError : String? = null,
    val photo : String? = "",
    val isLoading : Boolean = false,
    val userMessage : String? = null
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getProfile()
    }

    fun onSheetOpen(open: Boolean) {
        _uiState.update { it.copy(isSheetOpen = open)}
    }

    fun snackBarMessageShown() {
        _uiState.value = _uiState.value.copy(userMessage = null)
    }

    fun onNameChange(newName : String){
        val error = when {
            newName.isBlank() -> "Name cannot be empty"
            else -> null
        }
        _uiState.update { it.copy(name = newName,nameError = error) }
    }

    fun onPhoneNumberChange(newPhone : String){
        val error = when {
            newPhone.isBlank() -> "Phone Number cannot be empty"
            else -> null
        }
        _uiState.update { it.copy(phoneNumber = newPhone, phoneError = error) }
    }

    private fun getProfile () = viewModelScope.launch {
        getProfileUseCase.invoke().collect{ response ->
            when(response){
                is CinemaxResponse.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
                is CinemaxResponse.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is CinemaxResponse.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            userName = response.value.name,
                            name = response.value.name,
                            email = response.value.email,
                            phoneNumber = response.value.phoneNumber,
                            photo = response.value.photo
                        )
                    }
                }
            }
        }
    }

    fun uploadPhoto(imageUri: Uri) = viewModelScope.launch {

        if (imageUri.path.isNullOrEmpty()) {
            saveProfile()  // Langsung simpan profile jika tidak ada foto baru
            return@launch
        }

        _uiState.update { it.copy(isLoading = true) }  // Mulai loading

        try {
            // 1. Upload gambar dan dapatkan URL baru
            uploadImageUseCase.invoke(imageUri).collect { response ->
                when (response) {
                    is CinemaxResponse.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                photo = response.value, // Gunakan URL gambar
                                userMessage = "Profile photo updated successfully"
                            )
                        }
                        // save profile if upload is success
                        saveProfile()
                    }

                    is CinemaxResponse.Failure -> {
                        _uiState.update {
                            it.copy(isLoading = false, userMessage = "Profile photo updated failed")
                        }
                    }

                    is CinemaxResponse.Loading -> { _uiState.update { it.copy(isLoading = true) } }
                }
            }

        } catch (e: Exception) {
            _uiState.update {
                it.copy(isLoading = false, userMessage = e.message ?: "Upload failed")
            }
        }
    }

    private fun saveProfile() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        val userProfile = User(
            name = uiState.value.name,
            email = uiState.value.email,
            phoneNumber = uiState.value.phoneNumber,
            photo = uiState.value.photo
        )

        saveProfileUseCase(userProfile).collect { response ->
            _uiState.update {
                when (response) {
                    is CinemaxResponse.Success -> it.copy(isLoading = false, userMessage = response.value)
                    is CinemaxResponse.Failure -> it.copy(isLoading = false, userMessage = response.error)
                    is CinemaxResponse.Loading -> it.copy(isLoading = true)
                }
            }
        }
    }

    fun deleteProfile() {
        if (_uiState.value.photo.isNullOrEmpty()) {
            _uiState.update { it.copy(userMessage = "Please Add Profile Picture") }
            return
        }

        deleteProfileUseCase(_uiState.value.photo.toString())
            .onEach { response ->
                _uiState.update {
                    when (response) {
                        is CinemaxResponse.Failure -> it.copy(isLoading = false, userMessage = response.error)
                        is CinemaxResponse.Loading -> it.copy(isLoading = true)
                        is CinemaxResponse.Success -> it.copy(isLoading = false, photo = "", userMessage = response.value)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
