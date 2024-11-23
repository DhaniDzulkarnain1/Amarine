package com.app.amarine.ui.screen.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
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
import org.json.JSONObject

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

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
        errorMessage = null
        isLoading = true

        when {
            name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                errorMessage = "Semua field harus diisi!"
                isLoading = false
            }
            password != confirmPassword -> {
                errorMessage = "Password dan Konfirmasi Password tidak cocok!"
                isLoading = false
            }
            else -> {
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
                        isLoading = false

                        if (response.isSuccessful) {
                            showSuccessDialog = true
                        } else {
                            try {
                                // Parse error message from response body
                                val errorBody = response.errorBody()?.string()
                                val errorJson = JSONObject(errorBody ?: "")
                                errorMessage = errorJson.getString("error")
                            } catch (e: Exception) {
                                errorMessage = "Terjadi kesalahan tidak diketahui"
                                Log.e("RegisterScreen", "Error parsing error response: ${e.message}")
                            }
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
                        isLoading = false
                        errorMessage = "Gagal terhubung ke server. Silakan coba lagi."
                        Log.e("RegisterScreen", "Network error: ${t.message}")
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
        isLoading = isLoading,
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
    isLoading: Boolean,
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

        errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        MyTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = { Text(text = "Email") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        MyTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = { Text(text = "Nama Lengkap") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        MyTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = { Text(text = "Kata Sandi") },
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

        MyTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = { Text(text = "Konfirmasi Kata Sandi") },
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

        MyPrimaryButton(
            text = if (isLoading) "Memproses..." else "Daftar",
            onClick = onRegisterClick,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(top = 16.dp),
                color = Primary
            )
        }

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
                modifier = Modifier.clickable(onClick = onLoginClick)
            )
        }
    }
}