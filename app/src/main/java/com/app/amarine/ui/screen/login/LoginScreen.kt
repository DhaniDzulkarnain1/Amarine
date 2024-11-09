package com.app.amarine.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.amarine.R
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTextField
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.AmarineTheme
import com.app.amarine.ui.theme.Error
import com.app.amarine.ui.theme.Primary

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current

    LoginContent(
        email = email,
        password = password,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLoginClick = {
            navController.navigate(Screen.Home.route)
        },
        onForgotPasswordClick = {
            navController.navigate(Screen.ForgotPassword.route)
        },
        onRegisterClick = { navController.navigate(Screen.Register.route) },
        onGoogleClick = {
            try {
                viewModel.signInGoogle(context)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        },
    )
}

@Composable
fun LoginContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.bg_image_login),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()

        )
        Text(
            text = "Masuk",
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
        Text(
            text = "Lupa Kata Sandi?",
            style = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.End,
                color = Error
            ),
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 24.dp)
                .clickable(
                    onClick = onForgotPasswordClick
                )
        )
        Spacer(modifier = Modifier.height(32.dp))
        MyPrimaryButton(
            text = "Masuk",
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "Atau")
            Spacer(modifier = Modifier.width(24.dp))
            HorizontalDivider(
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_button_google),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(72.dp)
                .clickable(
                    onClick = onGoogleClick
                )
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Belum punya akun?",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Daftar",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.clickable(
                    onClick = onRegisterClick
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    AmarineTheme {
        LoginContent(
            email = "",
            password = "",
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = { /*TODO*/ },
            onRegisterClick = { /*TODO*/ },
            onForgotPasswordClick = { /*TODO*/ },
            onGoogleClick = { /*TODO*/ }
        )
    }
}