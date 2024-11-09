package com.app.amarine.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.amarine.R
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTextField
import com.app.amarine.ui.theme.Error
import com.app.amarine.ui.theme.Primary

@Composable
fun RegisterScreen(navController: NavController) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }

    RegisterContent(
        email = email,
        name = name,
        password = password,
        confirmPassword = confirmPassword,
        onEmailChange = { email = it },
        onNameChange = { name = it },
        onPasswordChange = { password = it },
        onConfirmPasswordChange = { confirmPassword = it },
        onRegisterClick = {  },
        onLoginClick = { navController.navigateUp() }
    )
}

@Composable
fun RegisterContent(
    email: String,
    name: String,
    password: String,
    confirmPassword: String,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var confirmPasswordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.bg_image_register),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = "Daftar",
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            ),
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(32.dp))
        MyTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = { Text(text = "Nama Lengkap") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = { Text(text = "Kata Sandi") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
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
            placeholder = { Text(text = "Konfirmasi Kata Sandi") },
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
        Spacer(modifier = Modifier.height(48.dp))
        MyPrimaryButton(
            text = "Daftar",
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Sudah punya akun?",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Masuk",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.clickable(
                    onClick = onLoginClick
                )
            )
        }
    }
}