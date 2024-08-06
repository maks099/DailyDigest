package com.daily.digest.ui.small_composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daily.digest.R

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit
){
    TextField(
        value = value,
        onValueChange = onChange,
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search, // ** Done. Close the keyboard **
            keyboardType = KeyboardType.Text
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.Black.copy(.6f),
                    fontSize = 16.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, shape = MaterialTheme.shapes.small),
    )
}