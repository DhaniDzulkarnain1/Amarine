package com.app.amarine.ui.screen.report_problem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.app.amarine.R
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun ReportProblemScreen(navController: NavController) {

    var isShowDialog by remember {
        mutableStateOf(false)
    }

    ReportProblemContent(
        onNavigateUp = { navController.navigateUp() },
        sendReport = { isShowDialog = true }
    )

    if (isShowDialog) {
        ResetSuccessDialog(
            onDismiss = {
                isShowDialog = false
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Composable
fun ReportProblemContent(
    onNavigateUp: () -> Unit,
    sendReport: () -> Unit,
    modifier: Modifier = Modifier
) {

    val text = remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Laporkan Masalah",
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
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    placeholder = {
                        Text(
                            text = "Ada Yang Bisa Kami Bantu?"
                        )
                    },
                    maxLines = Int.MAX_VALUE,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = "Kami akan merespon Anda melalui email"
                )

                Button(
                    onClick = sendReport,
                    modifier = Modifier
                        .height(48.dp)
                        .align(Alignment.End)
                ) {
                    Text(
                        text = "Kirim"
                    )
                }

            }
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
                    text = "Laporan Anda Telah Terkirim",
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