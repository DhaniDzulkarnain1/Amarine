package com.app.amarine.ui.screen.setting_about_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.components.TextDescription

@Composable
fun SettingAboutAppScreen(navController: NavController) {

    SettingAboutAppContent(
        onNavigateUp = { navController.navigateUp() }
    )
}

@Composable
fun SettingAboutAppContent(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemsFeatures = listOf(
        "Catat Hasil Tangkapan\n" + "Melihat dan mengelola hasil tangkapan dengan mudah.",
        "Kelola Stok\n" + "Catat, kelola dan lihat stok yang ada.",
        "Panduan atau Artikel\n" + "Akses panduan atau artikel secara praktis terkait tentang pengelolaan hasil tangkapan.",
        "Komunitas\n" + "Forum diskusi dan berbagi pengalaman sesama nelayan."
    )

    val itemsPrivilege = listOf(
        "Pengelolaan hasil tangkapan yang efisien dan terstruktur.",
        "Akses mudah ke panduan atau artikel terkini.",
        " Dukungan dari komunitas yang peduli dan siap membantu."
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Tentang Aplikasi",
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

        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Text(
                text = "Selamat Datang di Amarine!!",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            TextDescription(
                title = "Deskripsi",
                description = "Amarine adalah aplikasi untuk membantu nelayan mengelola hasil tangkapan,  melihat panduan atau artikel terkait perikanan dan berinteraksi di komunitas."
            )

            Text(
                text = "Fitur",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsFeatures.forEachIndexed { index, s ->
                    Text(
                        text = "${index + 1}. $s"
                    )
                }
            }

            TextDescription(
                title = "Tujuan Aplikasi",
                description = "Amarine bertujuan untuk membantu nelayan mengelola hasil tangkapan melalui teknologi digital."
            )

            Text(
                text = "Keunggulan",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsPrivilege.forEachIndexed { index, s ->
                    Text(
                        text = "${index + 1}. $s"
                    )
                }
            }

            TextDescription(
                title = "Kontak dan Dukungan",
                description = "Jika Anda memiliki pertanyaan atau butuh bantuan, jangan ragu hubungi kami di Amarine@gmail.com  atau kunjungi halaman Laporkan Masalah."
            )

        }
    }

}