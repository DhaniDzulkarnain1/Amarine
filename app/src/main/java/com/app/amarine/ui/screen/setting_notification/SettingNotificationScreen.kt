package com.app.amarine.ui.screen.setting_notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.theme.Primary200

@Composable
fun SettingNotificationScreen(navController: NavController) {

    SettingNotificationContent(
        onNavigateUp = {navController.navigateUp()}
    )

}

@Composable
fun SettingNotificationContent(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Notifikasi",
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

        var isOn by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Pemberitahuan notifikasi dari Forum Komunitas yang diikuti untuk memastikan anda mendapatkan informasi terbaru"
                )

                Text(
                    text = "Nonaktifkan notifikasi ketika ada informasi terbaru dari komunitas"
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (isOn) "Notifikasi Aktif" else "Notifikasi Mati"
                    )
                    Switch(
                        checked = isOn,
                        onCheckedChange = { isOn = it },
                    )
                }
            }
        }
    }
}