package com.example.cinemax.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.R
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.White

@Composable
fun CustomDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    icon: Painter,
    title: String,
    description: String
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF252836)
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(125.dp)
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                )
                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Grey,
                    modifier = Modifier.padding(8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = onConfirm,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = BlueAccent, containerColor = Color.Transparent),
                        modifier = Modifier.height(60.dp).weight(1f)
                    ) {
                        Text(text = "Log Out")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = BlueAccent, contentColor = White),
                        modifier = Modifier.height(60.dp).weight(1f)
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomDialog() {
    CustomDialog(
        onDismissRequest = {},
        onConfirm = {},
        onCancel = {},
        icon = painterResource(id = R.drawable.ic_question), // Replace with your icon resource
        title = "Are you sure ?",
        description = "Ullamcorper imperdiet urna id non sed est sem. Rhoncus amet, enim purus gravida donec aliquet."
    )
}
