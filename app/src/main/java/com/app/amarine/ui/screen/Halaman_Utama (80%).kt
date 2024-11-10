package com.example.amarine

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.amarine.ui.theme.AmarineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Content()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Content() {
    val orange = Color(0xFFFF620A)
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Amarine", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = orange)
            )
        },
        bottomBar = {
            BottomBar(selectedIndex = selectedIndex, onItemSelected = { selectedIndex = it })
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                when (selectedIndex) {
                    2 -> TabelInventaris() // Tampilkan tabel di bagian Home
                    else -> Text(
                        text = "Selected Tab: ${listOf("Home", "Community", "Profile", "Friends")[selectedIndex]}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    )
}

@Composable
fun TabelInventaris() {
    // Warna untuk header tabel dan latar belakang tabel
    val headerColor = Color(0xFFFF8532)
    val backgroundColor = Color(0xFFFFD2A5)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp) // Padding pada seluruh sisi halaman
            .background(Color.White) // Background halaman utama putih
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor) // Latar belakang tabel
                .padding(16.dp) // Padding di dalam tabel
        ) {
            // Header tabel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(headerColor) // Background header berwarna
            ) {
                Text(
                    text = "No",
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Jenis",
                    modifier = Modifier
                        .weight(2f)
                        .padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Kuantitas Tersedia",
                    modifier = Modifier
                        .weight(2f)
                        .padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Aksi",
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Data untuk baris tabel
            val data = listOf(
                listOf("1", "Buku", "50", "Edit"),
                listOf("2", "Pensil", "150", "Edit"),
                listOf("3", "Penghapus", "30", "Edit"),
                listOf("4", "Pulpen", "75", "Edit"),
                listOf("5", "Penggaris", "40", "Edit"),
                listOf("6", "Stapler", "20", "Edit"),
                listOf("7", "Kertas", "500", "Edit"),
                listOf("8", "Binder", "25", "Edit")
            )

            // Loop untuk menampilkan data di baris tabel
            data.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    // Menggunakan warna putih untuk kolom data
                    row.forEachIndexed { index, item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .weight(if (index == 0 || index == 3) 1f else 2f)
                                .padding(10.dp) // Padding lebih besar untuk kotak data
                                .background(Color.White), // Kotak putih untuk data
                            color = Color.Black // Warna teks putih untuk data
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun BottomBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf(
        BottomBarItemData("Home", Icons.Filled.Home),
        BottomBarItemData("Community", Icons.Filled.Home),
        BottomBarItemData("Profile", Icons.Filled.Person),
        BottomBarItemData("Friends", Icons.Filled.Home)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFFFF620A)),
                horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            BottomBarItem(
                icon = item.icon,
                title = item.title,
                isSelected = selectedIndex == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}

@Composable
fun BottomBarItem(icon: ImageVector, title: String, isSelected: Boolean, onClick: () -> Unit) {
    // Animasi untuk mengangkat ikon ke atas dan menambahkan latar belakang lingkaran
    val animatedOffset by animateDpAsState(
        targetValue = if (isSelected) -20.dp else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )
    val circleColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

    Box(
        modifier = Modifier
            .padding(8.dp)
            .offset(y = animatedOffset)
            .size(48.dp)
            .background(circleColor, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) Color.White else Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

data class BottomBarItemData(val title: String, val icon: ImageVector)