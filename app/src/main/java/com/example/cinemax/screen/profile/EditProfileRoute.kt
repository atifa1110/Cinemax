package com.example.cinemax.screen.profile

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinemax.R
import com.example.cinemax.component.CinemaxButton
import com.example.cinemax.component.CinemaxEmailField
import com.example.cinemax.component.CinemaxPasswordField
import com.example.cinemax.component.CinemaxPhoneField
import com.example.cinemax.component.CinemaxTextField
import com.example.cinemax.component.LoaderScreen
import com.example.cinemax.component.ProfileImageSelector
import com.example.core.ui.components.TopAppBar
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import java.io.File

@Composable
fun EditProfileRoute(
    onBackButtonClick: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    EditProfileScreen(
        uiState = uiState,
        imageUri = imageUri,
        onBackButtonClick = onBackButtonClick,
        onNameChange = {viewModel.onNameChange(it)},
        onPhoneChange = {viewModel.onPhoneNumberChange(it)},
        onSheetOpen = {viewModel.onSheetOpen(it)},
        onImageSelected = { imageUri = it },
        onDeleteProfile = { viewModel.deleteProfile() },
        uploadProfile = { viewModel.uploadPhoto(imageUri?: Uri.EMPTY)},
        snackBarMessageShown = { viewModel.snackBarMessageShown() }
    )
}

@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    imageUri: Uri?,
    onBackButtonClick: () -> Unit,
    onNameChange : (String) -> Unit,
    onPhoneChange : (String) -> Unit,
    onSheetOpen : (Boolean) -> Unit,
    onImageSelected : (Uri?) -> Unit,
    onDeleteProfile: () -> Unit,
    uploadProfile : () -> Unit,
    snackBarMessageShown : () -> Unit,
) {

    val snackBarHostState = remember { SnackbarHostState() }

    uiState.userMessage?.let { userMessage ->
        LaunchedEffect(userMessage) {
            snackBarHostState.showSnackbar(userMessage)
            snackBarMessageShown()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(title = R.string.edit_profile, onBackButton = onBackButtonClick)
        }
    ) {
        EditProfileContent(
            uiState = uiState,
            imageUri = imageUri,
            onNameChange = onNameChange,
            onPhoneChange = onPhoneChange,
            onSheetOpen = onSheetOpen,
            onImageSelected = onImageSelected,
            uploadProfile = uploadProfile,
            onDeleteProfile = onDeleteProfile,
            modifier = Modifier.padding(it)
        )

    }
}

@Composable
fun EditProfileContent(
    uiState: EditProfileUiState,
    imageUri: Uri?,
    onNameChange : (String) -> Unit,
    onPhoneChange : (String) -> Unit,
    onSheetOpen : (Boolean) -> Unit,
    onImageSelected : (Uri?) -> Unit,
    uploadProfile : () -> Unit,
    onDeleteProfile : () -> Unit,
    modifier: Modifier = Modifier
){
    if(uiState.isLoading){
        LoaderScreen(modifier = Modifier.fillMaxSize().background(Dark))
    }else {
        Column(
            modifier
                .fillMaxSize()
                .background(Dark)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileImageSelector(
                uiState = uiState,
                onSheetOpen = onSheetOpen,
                imageUri = imageUri,
                onImageSelected = onImageSelected,
                onDeleteProfile = onDeleteProfile
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = uiState.userName.toString(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp),
                text = uiState.email.toString(),
                textAlign = TextAlign.Center,
                color = Gray,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

            CinemaxTextField(
                text = uiState.name.toString(),
                textError = null,
                labelName = R.string.full_name,
                onTextChange = onNameChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            CinemaxEmailField(
                text = uiState.email.toString(),
                textError = null,
                labelName = R.string.email_address,
                onTextChange = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            CinemaxPasswordField(
                password = "12345678",
                passwordError = null,
                labelName = R.string.password,
                onPasswordChange = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            CinemaxPhoneField(
                phone = uiState.phoneNumber.toString(),
                phoneError = uiState.phoneError,
                labelName = R.string.phone_number,
                onPhoneChange = onPhoneChange
            )

            Spacer(modifier = Modifier.height(32.dp))

            CinemaxButton(
                title = R.string.save_changes,
                onButtonClick = {
                    //saveProfileUser()
                    uploadProfile()
                }
            )
        }
    }
}

fun Context.createImageFile(): File {
    // create an image file name
    val imageFileName = "profile-${System.currentTimeMillis()}"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir /* directory */
    )
}


@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    CinemaxTheme {
            EditProfileScreen(
                uiState = EditProfileUiState(
                    isSheetOpen = true,
                    userName = "Atifa Fiorenza",
                    email = "atifafiorenza24@gmail.com",
                    phoneNumber = "87878601919"
                ),
                imageUri = null,
                onNameChange = {},
                onPhoneChange = {},
                onSheetOpen = {},
                onImageSelected = {},
                onDeleteProfile = {},
                uploadProfile = {},
                onBackButtonClick = {},
                snackBarMessageShown = {}
            )
    }
}