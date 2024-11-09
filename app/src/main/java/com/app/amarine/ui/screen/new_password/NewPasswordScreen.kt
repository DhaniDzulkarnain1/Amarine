package com.app.amarine.ui.screen.new_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.app.amarine.R
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTextField
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun NewPasswordScreen(navController: NavController) {
    var newPassword by remember {
        mutableStateOf("")
    }
    var newConfirmPassword by remember {
        mutableStateOf("")
    }
    var isShowDialog by remember {
        mutableStateOf(false)
    }

    NewPasswordContent(
        newPassword = newPassword,
        newConfirmPassword = newConfirmPassword,
        onNewPasswordChange = { newPassword = it },
        onNewConfirmPasswordChange = { newConfirmPassword = it },
        onNavigateUp = { navController.navigateUp() },
        onUpdatePasswordClick = { isShowDialog = true }
    )

    if (isShowDialog) {
        ResetSuccessDialog(
            onDismiss = {
                isShowDialog = false
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Composable
fun NewPasswordContent(
    newPassword: String,
    newConfirmPassword: String,
    onNewPasswordChange: (String) -> Unit,
    onNewConfirmPasswordChange: (String) -> Unit,
    onNavigateUp: () -> Unit,
    onUpdatePasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                title = "",
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
                text = "Kata sandi baru",
                style = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Buat kata sandi baru. Pastikan kata sandi tersebut berbeda dengan kata sandi sebelumnya untuk keamanan",
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            MyTextField(
                value = newPassword,
                onValueChange = onNewPasswordChange,
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
                value = newConfirmPassword,
                onValueChange = onNewConfirmPasswordChange,
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
            Spacer(modifier = Modifier.height(32.dp))
            MyPrimaryButton(
                text = "Update Kata Sandi",
                onClick = onUpdatePasswordClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}

@Composable
fun ResetSuccessDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dismiss by rememberUpdatedState(newValue = onDismiss)
    LaunchedEffect(key1 = dismiss) {
        delay(2.seconds)
        onDismiss()
    }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Berhasil",
                    style = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Selamat! Kata sandi anda berhasil diubah. Anda akan diarahkan ke halaman masuk sekarang",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 56.dp)
                )
            }
        }
    }
}