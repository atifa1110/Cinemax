package com.example.cinemax.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Soft
import com.example.cinemax.ui.theme.White

@Composable
fun CinemaxTextField(
    text : String,
    textError : String? = null,
    @StringRes labelName : Int,
    onTextChange : (String) -> Unit,
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        label = { Text(stringResource(labelName), color = White) },
        singleLine = true,
        modifier = Modifier.testTag("TextInput")
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Grey,
            focusedLabelColor = White,
            unfocusedTextColor = Grey,
            unfocusedLabelColor = White,
            errorTextColor = Grey,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedBorderColor = Soft,
            unfocusedBorderColor = Soft,
        ),
        shape = RoundedCornerShape(24.dp),
        isError = textError!=null
    )

    if(textError!=null){
        Text(
            text = "* $textError",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 8.dp,top = 4.dp).testTag("TextError")
        )
    }
}

@Composable
fun CinemaxEmailField(
    text : String,
    textError : String? = null,
    @StringRes labelName : Int,
    onTextChange : (String) -> Unit,
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        label = { Text(stringResource(labelName), color = White) },
        singleLine = true,
        modifier = Modifier.testTag("EmailInput")
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Grey,
            focusedLabelColor = White,
            unfocusedTextColor = Grey,
            unfocusedLabelColor = White,
            errorTextColor = Grey,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedBorderColor = Soft,
            unfocusedBorderColor = Soft,
        ),
        shape = RoundedCornerShape(24.dp),
        isError = textError!=null
    )

    if(textError!=null){
        Text(
            text = "* $textError",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 8.dp,top = 4.dp).testTag("EmailError")
        )
    }
}

@Composable
fun CinemaxPasswordField(
    password : String,
    passwordError : String? = null,
    @StringRes labelName: Int,
    onPasswordChange : (String) -> Unit,
) {

    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        label = { Text(stringResource(labelName), color = White) },
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = null, tint = Grey)
            }
        },
        modifier = Modifier.fillMaxWidth().testTag("PasswordInput"),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Grey,
            focusedLabelColor = White,
            unfocusedTextColor = Grey,
            unfocusedLabelColor = White,
            errorTextColor = Grey,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedBorderColor = Soft,
            unfocusedBorderColor = Soft
        ),
        shape = RoundedCornerShape(24.dp),
        isError = passwordError!=null
    )

    if(passwordError!=null){
        Text(
            text = "* $passwordError" ,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Start,
            modifier = Modifier.testTag("PasswordError")
                .fillMaxWidth()
                .padding(start = 8.dp, top = 4.dp)
        )
    }
}

@Composable
fun CinemaxPhoneField(
    phone : String,
    phoneError : String? = null,
    @StringRes labelName: Int,
    onPhoneChange : (String) -> Unit,
) {
    OutlinedTextField(
        value = phone,
        onValueChange = { onPhoneChange(it) },
        label = { Text(stringResource(labelName), color = White) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().testTag("PhoneInput"),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Grey,
            focusedLabelColor = White,
            unfocusedTextColor = Grey,
            unfocusedLabelColor = White,
            errorTextColor = Grey,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedBorderColor = Soft,
            unfocusedBorderColor = Soft
        ),
        shape = RoundedCornerShape(24.dp),
        isError = phoneError!=null,
        leadingIcon = { Text("+62") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )

    if(phoneError!=null){
        Text(
            text = "* $phoneError",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 4.dp)
        )
    }
}