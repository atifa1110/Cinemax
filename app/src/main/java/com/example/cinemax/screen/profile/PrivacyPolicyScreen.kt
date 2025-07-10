package com.example.cinemax.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemax.R
import com.example.core.ui.components.TopAppBar
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.White

@Composable
fun PrivacyPolicyScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = R.string.privacy_policy, onBackButton={} )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Dark).padding(16.dp)
            .padding(it)){

            Text(
                text = stringResource(R.string.terms),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.term_describe),
                textAlign = TextAlign.Justify,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Grey
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(R.string.change_service),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.term_describe),
                textAlign = TextAlign.Justify,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Grey
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrivacyPolicyPreview() {
    CinemaxTheme {
            PrivacyPolicyScreen()
    }
}