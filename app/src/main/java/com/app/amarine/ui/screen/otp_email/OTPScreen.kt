package com.app.amarine.ui.screen.otp_email

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.components.OTPInputTextFields
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.Primary

@Composable
fun OTPScreen(navController: NavController) {
    val otpValues = remember { mutableStateListOf("", "", "", "", "") }
    var isOtpError by remember {
        mutableStateOf(false)
    }

    OTPContent(
        otpValues = otpValues,
        isOtpError = isOtpError,
        onUpdateOtpValuesByIndex = { index, value ->
            isOtpError = false
            otpValues[index] = value
        },
        onNavigateUp = { navController.navigateUp() },
        onOtpConfirmClick = {
            val isOtpValid = otpValues.all { it.isNotEmpty() }
            isOtpError = !isOtpValid
            if (isOtpValid) {
                navController.navigate(Screen.ResetPassword.route)
            }
        },
        onResendOtpClick = {}
    )
}

@Composable
fun OTPContent(
    otpValues: List<String>,
    isOtpError: Boolean,
    onUpdateOtpValuesByIndex: (Int, String) -> Unit,
    onNavigateUp: () -> Unit,
    onOtpConfirmClick: () -> Unit,
    onResendOtpClick: () -> Unit,
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
                text = "Cek Email Anda",
                style = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Masukkan 5 digit kode OTP",
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                ),
                minLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            OTPInputTextFields(
                otpValues = otpValues,
                otpLength = 5,
                isError = isOtpError,
                onUpdateOtpValuesByIndex = onUpdateOtpValuesByIndex,
                onOtpInputComplete = { /*TODO*/ },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            MyPrimaryButton(
                text = "Verifikasi Kode",
                onClick = onOtpConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Belum mendapat email?",
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kirim ulang",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Primary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable(
                        onClick = onResendOtpClick
                    )
                )
            }
        }
    }
}