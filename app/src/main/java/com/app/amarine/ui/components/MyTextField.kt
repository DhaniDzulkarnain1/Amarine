package com.app.amarine.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import com.app.amarine.ui.theme.TextFieldContainerColor

@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    isError: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        modifier = modifier.clip(MaterialTheme.shapes.large),
        singleLine = singleLine,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = TextFieldContainerColor,
            unfocusedContainerColor = TextFieldContainerColor,
            disabledContainerColor = TextFieldContainerColor,
            focusedIndicatorColor = Color.Transparent, // Remove underline color
            unfocusedIndicatorColor = Color.Transparent, // Remove underline color
            disabledIndicatorColor = Color.Transparent,
        ),
        maxLines = maxLines,
        isError = isError,
        trailingIcon = trailingIcon,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon,
        visualTransformation = visualTransformation,
    )
}