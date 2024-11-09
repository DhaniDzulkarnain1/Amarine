package com.app.amarine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.amarine.ui.theme.AmarineTheme
import com.app.amarine.ui.theme.TextFieldContainerColor

@Composable
fun MySearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable () -> Unit = { Icon(imageVector = Icons.Rounded.Search, contentDescription = null) },
) {
    Box(
        modifier = modifier
            .background(Color.White, MaterialTheme.shapes.large)
            .shadow(2.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(onClick = { onValueChange("") }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent, // Remove underline color
                unfocusedIndicatorColor = Color.Transparent, // Remove underline color
                disabledIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MySearchBarPreview() {
    AmarineTheme {
        MySearchBar(value = "", onValueChange = {}, placeholder = { Text(text = "Type here...") })
    }
}