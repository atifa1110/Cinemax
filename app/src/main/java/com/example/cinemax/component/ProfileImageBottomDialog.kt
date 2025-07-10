package com.example.cinemax.component

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.cinemax.screen.profile.EditProfileUiState
import com.example.cinemax.screen.profile.createImageFile
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Soft
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImageSelector(
    uiState: EditProfileUiState,
    onSheetOpen : (Boolean) -> Unit,
    imageUri: Uri?,
    onImageSelected: (Uri?) -> Unit,
    onDeleteProfile : () -> Unit
) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Image pickers for gallery and camera
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uris: Uri? ->
        onImageSelected(uris)
        onSheetOpen(false)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        onImageSelected(uri)
        onSheetOpen(false)
    }

    // Bottom Sheet content for Material 3
    if(uiState.isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { onSheetOpen(false) },
            sheetState = rememberModalBottomSheetState(),
            containerColor = Soft,
            content = {
                Column(modifier = Modifier
                    .fillMaxWidth().testTag("ModalBottomSheet")
                    .padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        modifier = Modifier.testTag("SelectGallery"),
                        onClick = { galleryLauncher.launch("image/*") }) {
                        Text("Select from Gallery", color = Color.White)
                    }


                    TextButton(
                        modifier = Modifier.testTag("TakePhoto"),
                        onClick = {
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(uri)
                            } else {
                                // Request a permission
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    ) {
                        Text("Take a Photo", color = Color.White)
                    }


                    TextButton(
                        modifier = Modifier.testTag("DeletePhoto"),
                        onClick = {
                        onDeleteProfile()
                        onSheetOpen(false)
                    }) {
                        Text("Delete Photo", color = Color.White)
                    }

                }
            },
        )
    }

    // Profile Image Column (Clickable to open bottom sheet)
    Column(
        modifier = Modifier.testTag("SelectImage")
            .padding(10.dp)
            .clickable { onSheetOpen(true) }
            .size(88.dp)
            .clip(CircleShape)
            .background(Soft),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            !uiState.photo.isNullOrEmpty() -> {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.FillWidth,
                    model = uiState.photo,
                    contentDescription = "Profile Image"
                )
            }
            imageUri != null -> {
                AsyncImage(
                    modifier = Modifier.testTag("ProfileImageUri")
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    model = imageUri,
                    contentDescription = "Selected Image"
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.Default.PermIdentity,
                    tint = Grey,
                    contentDescription = "Default Profile Icon"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileBottomDialogPreview(){
    CinemaxTheme {
        Surface {
            ProfileImageSelector(
                uiState = EditProfileUiState(isSheetOpen = true),
                onSheetOpen = {},
                imageUri = null,
                onImageSelected = {},
                onDeleteProfile = {}
            )
        }
    }
}