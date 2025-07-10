package com.example.cinemax.screen.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemax.R
import com.example.core.ui.components.TopAppBar
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Soft

@Composable
fun NotificationScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = R.string.notification, onBackButton = {})
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Dark)
                .padding(16.dp)
                .padding(it)
        ) {
            NotificationSection()
        }
    }
}

@Composable
fun NotificationSection(){
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Soft,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.messages_notifications),
                color = Color(0xFF92929D),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
            NotificationItem(title = stringResource(R.string.show_notifications),showNotification = true)
            HorizontalDivider(color = Soft.copy(alpha = 0.3f), thickness = 1.dp)
            NotificationItem(title = stringResource(R.string.exceptions), showNotification = false)
        }
    }
}

@Composable
fun NotificationItem(title: String, showNotification: Boolean) {
    var isChecked by remember { mutableStateOf(true) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Text(modifier = Modifier.weight(1f),
            text = title, color = Color.White,
            fontSize = 16.sp, fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(16.dp))
        if(showNotification) {
            Switch(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = BlueAccent,
                    uncheckedThumbColor = Grey
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationPreview() {
    CinemaxTheme {
            NotificationScreen()
    }
}