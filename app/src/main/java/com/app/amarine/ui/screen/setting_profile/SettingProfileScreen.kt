package com.app.amarine.ui.screen.setting_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.ui.components.ButtonSetting
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen

@Composable
fun SettingProfileScreen(
    navController: NavController
) {
    SettingProfileContent(
        onNavigateUp = { navController.navigateUp() },
        onNotification = { navController.navigate(Screen.SettingNotification.route) },
        onProblem = { navController.navigate(Screen.SettingReportProblem.route) },
        onAboutApp = { navController.navigate(Screen.SettingAboutApp.route) }
    )
}

@Composable
fun SettingProfileContent(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onNotification: () -> Unit,
    onProblem: () -> Unit,
    onAboutApp: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Pengaturan",
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
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                ButtonSetting(
                    text = "Notifikasi",
                    onClick = onNotification

                )

                ButtonSetting(
                    text = "Laporkan Masalah",
                    onClick = onProblem

                )

                ButtonSetting(
                    text = "Tentang Aplikasi",
                    onClick = onAboutApp

                )
            }
        }
    }
}