package com.example.cinemax.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.cinemax.R
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Soft
import com.example.cinemax.ui.theme.White

@Composable
fun SearchHome(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier : Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current
) {
    Box(Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp)){
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_title),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            maxLines = 1,
            value = query,
            onValueChange = onQueryChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Soft,
                focusedTextColor = White,
                unfocusedTextColor = White,
                focusedLabelColor = Grey,
                unfocusedContainerColor = Soft,
                disabledContainerColor = Soft,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(30.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Grey
                )
            },
            trailingIcon = {
                Row(modifier = modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "|", color = Grey)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        tint = White,
                        contentDescription = "filter"
                    )
                }
            }
        )
    }
}