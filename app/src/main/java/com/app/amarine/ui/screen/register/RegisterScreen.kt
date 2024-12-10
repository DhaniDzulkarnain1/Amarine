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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.app.amarine.ApiService
import com.app.amarine.R
import com.app.amarine.model.RegisterRequest
import com.app.amarine.model.RegisterResponse
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTextField
import com.app.amarine.ui.theme.Primary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private val _registerState = MutableStateFlow<Result<RegisterResponse>>(Result.Initial)
    val registerState: StateFlow<Result<RegisterResponse>> = _registerState.asStateFlow()

    fun register(email: String, password: String, nama: String) {
        viewModelScope.launch {
            try {
                _registerState.value = Result.Loading
                val registerRequest = RegisterRequest(
                    email = email,
                    password = password,
                    nama = nama
                )
                val response = apiService.register(registerRequest)

                if (response.isSuccessful && response.body() != null) {
                    _registerState.value = Result.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    _registerState.value = Result.Error(
                        try {
                            JSONObject(errorBody ?: "").getString("error")
                        } catch (e: Exception) {
                            "Terjadi kesalahan tidak diketahui"
                        }
                    )
                }
            } catch (e: Exception) {
                _registerState.value = Result.Error("Gagal terhubung ke server. Silakan coba lagi.")
            }
        }
    }
}

sealed class Result<out T> {
    object Initial : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val registerState = viewModel.registerState.collectAsState().value

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

    LaunchedEffect(registerState) {
        when (registerState) {
            is Result.Success -> {
                showSuccessDialog = true
            }
            is Result.Error -> {
                errorMessage = registerState.message
            }
            else -> {}
        }
    }

    RegisterContent(
        email = email,
        name = name,
        password = password,
        confirmPassword = confirmPassword,
        errorMessage = errorMessage,
        isLoading = registerState is Result.Loading,
        onEmailChange = { email = it },
        onNameChange = { name = it },
        onPasswordChange = { password = it },
        onConfirmPasswordChange = { confirmPassword = it },
        onRegisterClick = {
            errorMessage = null
            when {
                name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                    errorMessage = "Semua field harus diisi!"
                }
                password != confirmPassword -> {
                    errorMessage = "Password dan Konfirmasi Password tidak cocok!"
                }
                else -> {
                    viewModel.register(email, password, name)
                }
            }
        },
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
                        contentDescription = if (passwordVisible) "Sembunyikan password" else "Tampilkan password"
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
                        contentDescription = if (confirmPasswordVisible) "Sembunyikan password" else "Tampilkan password"
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