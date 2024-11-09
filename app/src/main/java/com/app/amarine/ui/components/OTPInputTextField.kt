package com.app.amarine.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.amarine.ui.theme.TextFieldContainerColor

@Composable
fun OTPInputTextFields(
    otpLength: Int,
    onUpdateOtpValuesByIndex: (Int, String) -> Unit,
    onOtpInputComplete: () -> Unit,
    modifier: Modifier = Modifier,
    otpValues: List<String> = List(otpLength) { "" }, // Pass this as default for future reference
    isError: Boolean = false,
) {
    val focusRequesters = List(otpLength) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
//        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
    ) {
        otpValues.forEachIndexed { index, value ->
            TextField(
                modifier = Modifier
                    .weight(1f)
//                    .width(64.dp)
                    .padding(6.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Backspace) {
                            if (otpValues[index].isEmpty() && index > 0) {
                                onUpdateOtpValuesByIndex(index, "")
                                focusRequesters[index - 1].requestFocus()
                            } else {
                                onUpdateOtpValuesByIndex(index, "")
                            }
                            true
                        } else {
                            false
                        }
                    },
                value = value,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = TextFieldContainerColor,
                    unfocusedContainerColor = TextFieldContainerColor,
                    disabledContainerColor = TextFieldContainerColor,
                    focusedIndicatorColor = Color.Transparent, // Remove underline color
                    unfocusedIndicatorColor = Color.Transparent, // Remove underline color
                    disabledIndicatorColor = Color.Transparent,
                ),
                onValueChange = { newValue ->
                    // To use OTP code copied from keyboard
                    if (newValue.length == otpLength) {
                        for (i in otpValues.indices) {
                            onUpdateOtpValuesByIndex(
                                i,
                                if (i < newValue.length && newValue[i].isDigit()) newValue[i].toString() else ""
                            )
                        }

                        keyboardController?.hide()
                        onOtpInputComplete() // you should validate the otp values first for, if it is only digits or isNotEmpty
                    } else if (newValue.length <= 1) {
                        onUpdateOtpValuesByIndex(index, newValue)
                        if (newValue.isNotEmpty()) {
                            if (index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            } else {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                onOtpInputComplete()
                            }
                        }
                    } else {
                        if (index < otpLength - 1) focusRequesters[index + 1].requestFocus()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index == otpLength - 1) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        if (index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    },
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onOtpInputComplete()
                    }
                ),
                shape = MaterialTheme.shapes.large,
                isError = isError,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            )

            LaunchedEffect(value) {
                if (otpValues.all { it.isNotEmpty() }) {
                    focusManager.clearFocus()
                    onOtpInputComplete()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}


// Reference for using OTP input fields

/*
// In Screen, inside Composable function,
OTPInputTextFields(
    otpLength = signUpUiState.otpLength,
    otpValues = signUpUiState.otpValues,
    isError = signUpUiState.isOtpError,
    onUpdateOtpValuesByIndex = { index, value ->
        screenModel.updateOtpValue(index, value)
    },
    onOtpInputComplete = {
        // Check for the otp validation
        if (screenModel.isOtpInputValuesAreValid()) {
            showLoadingDialog = true
            // Call the API for OTP verification and perform any necessary actions
            coroutineScope.launch {
                if ( /*If API response is success*/ ) {
                    showLoadingDialog = false
                    showSuccessDialogWithSuccessMessage = true
                    // After success, navigate to next screen
                } else {
                    showErrorMessage() // Response with error message
                }
            }
        }
    }
)
// In ViewModel,
data class SignUpUiState(
    val otpLength: Int = 6,
    val isOtpError: Boolean = false,
    val otpValues: List<String> = List(otpLength) { "" }
}
fun updateOtpValue(index: Int, value: String) {
    val newOtpValues = _signUpUiState.value.otpValues.toMutableList() // Making list mutable to update value
    newOtpValues[index] = value // Update value at the specified index
    _signUpUiState.update { currentState ->
        currentState.copy(
            otpValues = newOtpValues,
            isOtpError = false
        )
    }
}
*/



// Separate from above (RECOMMENDED ONLY FOR TESTING)

@Composable
fun OtpInputTestFunction(modifier: Modifier = Modifier) {
    Column(modifier) {
        // Inside composition, use this only for testing.
        // I recommend to do it through viewModel like above.
        val otpValues =
            remember { mutableStateListOf<String>("", "", "", "", "", "") }

        OTPInputTextFields(
            otpValues = otpValues,
            otpLength = 6,
            onOtpInputComplete = { /* TODO: Make api calls or anything after validation */ },
            onUpdateOtpValuesByIndex = { index, value ->
                otpValues[index] = value
            }
        )

        // To print or check
        Text(otpValues.joinToString())
    }
}