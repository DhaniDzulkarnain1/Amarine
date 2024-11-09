package com.app.amarine.ui.screen.change_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTextField
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.screen.add_note.AddNoteContent
import com.app.amarine.ui.theme.AmarineTheme

@Composable
fun ChangePasswordScreen(navController: NavController) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    ChangePasswordContent(
        currentPassword = currentPassword,
        newPassword = newPassword,
        confirmPassword = confirmPassword,
        onCurrentPasswordChange = { currentPassword = it },
        onNewPasswordChange = { newPassword = it },
        onConfirmPasswordChange = { confirmPassword = it },
        onNavigateUp = { navController.navigateUp() },
        onNextClick = { navController.navigate(Screen.NewPassword.route) }
    )
}

@Composable
fun ChangePasswordContent(
    currentPassword: String,
    newPassword: String,
    confirmPassword: String,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onNavigateUp: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPasswordVisible by remember {
        mutableStateOf(false)
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var confirmPasswordVisible by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Ubah Kata Sandi",
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Kata sandi baru minimal harus lebih dari 8 karakter.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            MyTextField(
                value = currentPassword,
                onValueChange = onCurrentPasswordChange,
                placeholder = { Text(text = "Kata Sandi Lama") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (!currentPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { currentPasswordVisible = !currentPasswordVisible }) {
                        Icon(
                            imageVector = if (currentPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyTextField(
                value = newPassword,
                onValueChange = onNewPasswordChange,
                placeholder = { Text(text = "Kata Sandi Baru") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                placeholder = { Text(text = "Konfirmasi Kata Sandi Baru") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (!confirmPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            MyPrimaryButton(
                text = "Ubah Kata Sandi",
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ChangePasswordScreenPreview() {
    AmarineTheme {
        ChangePasswordContent(
            currentPassword = "",
            newPassword = "",
            confirmPassword = "",
            onCurrentPasswordChange = {},
            onNewPasswordChange = {},
            onConfirmPasswordChange = {},
            onNavigateUp = { /*TODO*/ },
            onNextClick = { /*TODO*/ })
    }
}