package com.app.amarine.ui.screen.forgot_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTextField
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.AmarineTheme

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by rememberSaveable {
        mutableStateOf("")
    }

    ForgotPasswordContent(
        email = email,
        onEmailChange = { email = it },
        onNavigateUp = { navController.navigateUp() },
        onForgotPasswordClick = { navController.navigate(Screen.OTPEmail.route) }
    )
}

@Composable
fun ForgotPasswordContent(
    email: String,
    onEmailChange: (String) -> Unit,
    onNavigateUp: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                text = "Lupa Kata Sandi",
                style = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Silahkan masukkan email anda untuk mengatur ulang kata sandi",
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp)
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
            Spacer(modifier = Modifier.height(32.dp))
            MyPrimaryButton(
                text = "Reset Kata Sandi",
                onClick = onForgotPasswordClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    AmarineTheme {
        ForgotPasswordContent(
            email = "",
            onEmailChange = { /*TODO*/ },
            onNavigateUp = { /*TODO*/ },
            onForgotPasswordClick = { /*TODO*/ }
        )
    }
}