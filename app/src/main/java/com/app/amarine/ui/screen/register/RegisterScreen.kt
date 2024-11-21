package com.app.amarine.ui.screen.register

import android.util.Log
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.app.amarine.RetrofitClient
import com.app.amarine.model.RegisterRequest
import com.app.amarine.model.RegisterResponse
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTextField
import com.app.amarine.ui.theme.Primary

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Dialog sukses
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
            },
            title = { Text("Berhasil") },
            text = { Text("Pendaftaran berhasil! Silahkan login.") },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }) {
                    Text("OK")
                }
            }
        )
    }

    val onRegisterClick: () -> Unit = {
        // Reset error message
        errorMessage = null

        when {
            name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                errorMessage = "Semua field harus diisi!"
                Log.e("RegisterScreen", "Validasi gagal: Semua field harus diisi!")
            }
            password != confirmPassword -> {
                errorMessage = "Password dan Konfirmasi Password tidak cocok!"
                Log.e("RegisterScreen", "Validasi gagal: Password tidak cocok!")
            }
            else -> {
                // Hanya kirim email, password, dan nama (tanpa role)
                val registerRequest = RegisterRequest(
                    email = email,
                    password = password,
                    nama = name
                )
                val call = RetrofitClient.instance.register(registerRequest)

                call.enqueue(object : retrofit2.Callback<RegisterResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<RegisterResponse>,
                        response: retrofit2.Response<RegisterResponse>
                    ) {
                        Log.d("RegisterScreen", "Response Code: ${response.code()}")
                        Log.d("RegisterScreen", "Error Body: ${response.errorBody()?.string()}")
                        Log.d("RegisterScreen", "URL Called: ${call.request().url}")

                        if (response.isSuccessful) {
                            showSuccessDialog = true
                        } else {
                            errorMessage = "Gagal mendaftar: ${response.message()}"
                            Log.e("RegisterScreen", "Gagal mendaftar: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
                        errorMessage = "Error: ${t.message}"
                        Log.e("RegisterScreen", "Error: ${t.message}")
                    }
                })
            }
        }
    }

    RegisterContent(
        email = email,
        name = name,
        password = password,
        confirmPassword = confirmPassword,
        errorMessage = errorMessage,
        onEmailChange = { email = it },
        onNameChange = { name = it },
        onPasswordChange = { password = it },
        onConfirmPasswordChange = { confirmPassword = it },
        onRegisterClick = onRegisterClick,
        onLoginClick = {
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }
    )
}

@Composable
fun RegisterContent(
    email: String,
    name: String,
    password: String,
    confirmPassword: String,
    errorMessage: String?,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.bg_image_register),
            contentDescription = "Register Background",
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
            modifier = Modifier.padding(top = 16.dp)
        )

        // Error message if any
        errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Email field
        MyTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Name field
        MyTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = { Text(text = "Nama Lengkap") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password field
        MyTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = { Text(text = "Kata Sandi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password field
        MyTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = { Text(text = "Konfirmasi Kata Sandi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (!confirmPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                        contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(48.dp))

        // Register Button
        MyPrimaryButton(
            text = "Daftar",
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Login Link
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
                modifier = Modifier.clickable(onClick = onLoginClick)
            )
        }
    }
}