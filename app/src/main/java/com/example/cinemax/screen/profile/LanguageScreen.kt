package com.example.cinemax.screen.profile

import com.example.cinemax.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.components.TopAppBar
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Soft

@Composable
fun LanguageSelectionScreen() {
    var selectedLanguage by remember { mutableStateOf("English (UK)") }

    val suggestedLanguages = listOf("English (UK)", "English", "Bahasa Indonesia")
    val otherLanguages = listOf("Chinese", "Croatian", "Czech", "Danish", "Filipino", "Finnish")

    Scaffold (
        topBar = {
            TopAppBar(
                title = R.string.language,
                onBackButton = {})
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Dark)
                .padding(16.dp).padding(it)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        width = 1.dp, color = Soft,
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                )
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top =16.dp),
                        text = stringResource(R.string.suggested_languages),
                        color = Color(0xFF92929D),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                    suggestedLanguages.forEach { language ->
                        LanguageListItem(
                            language = language,
                            isSelected = language == selectedLanguage,
                            onLanguageSelected = { selectedLanguage = it }
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        width = 1.dp, color = Soft,
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                )
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp),
                        text = stringResource(R.string.other_languages),
                        color = Color(0xFF92929D),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                    otherLanguages.forEach { language ->
                        LanguageListItem(
                            language = language,
                            isSelected = language == selectedLanguage,
                            onLanguageSelected = { selectedLanguage = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LanguageListItem(
    language: String, isSelected: Boolean,
    onLanguageSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLanguageSelected(language) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = language,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = BlueAccent
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLanguageSelectionScreen() {
    LanguageSelectionScreen()
}
