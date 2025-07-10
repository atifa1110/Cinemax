package com.example.cinemax.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinemax.R
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Soft
import com.example.cinemax.ui.theme.White

@Composable
fun CinemaxButton(
    @StringRes title : Int,
    onButtonClick : () -> Unit,
) {
    Button(
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueAccent,
            disabledContainerColor = Soft,
            contentColor = White,
            disabledContentColor = Grey
        ),
        modifier = Modifier.testTag("CinemaxButton")
            .fillMaxWidth()
            .height(56.dp),
        shape = CircleShape
    ) {
        Text(text = stringResource(id = title), color = White)
    }
}

@Composable
fun AuthCinemaxButton(
    @StringRes title : Int,
    onButtonClick : () -> Unit,
    email: String,
    password : String,
) {
    Button(
        enabled = email.isNotEmpty() && password.isNotEmpty(),
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueAccent,
            disabledContainerColor = Soft,
            contentColor = White,
            disabledContentColor = Grey
        ),
        modifier = Modifier.testTag("AuthButton")
            .fillMaxWidth()
            .height(56.dp),
        shape = CircleShape
    ) {
        Text(text = stringResource(id = title))
    }
}


@Preview(showBackground = true)
@Composable
fun LoginButtonPreview(){
    CinemaxTheme {
            AuthCinemaxButton(title = R.string.login, onButtonClick = {},
                email = "", password = ""
            )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterButtonPreview(){
    CinemaxTheme {
            CinemaxButton(title = R.string.sign_up, onButtonClick = {})
    }
}