package com.app.amarine.ui.screen.reset_password

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTextField
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen

@Composable
fun ResetPasswordScreen(navController: NavController) {
    ResetPasswordContent(
        onNavigateUp = { navController.navigateUp() },
        onConfirmClick = { navController.navigate(Screen.NewPassword.route ) }
    )
}

@Composable
fun ResetPasswordContent(
    onNavigateUp: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                text = "Reset kata sandi",
                style = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Kata sandi anda berhasil diatur ulang. Klik konfirmasi untuk membuat kata sandi baru",
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            MyPrimaryButton(
                text = "Konfirmasi",
                onClick = onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}