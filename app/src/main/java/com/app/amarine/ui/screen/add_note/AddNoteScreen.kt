package com.app.amarine.ui.screen.add_note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.model.Note
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.theme.AmarineTheme
import com.app.amarine.ui.theme.Primary200

@Composable
fun AddNoteScreen(
    navController: NavController
) {
    AddNoteContent(
        onNavigateUp = { navController.navigateUp() }
    )
}

@Composable
fun AddNoteContent(
    onNavigateUp : () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Tambah Catatan",
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            val name = remember { mutableStateOf("") }
            val type = remember { mutableStateOf("") }
            val weight = remember { mutableStateOf("") }
            val date = remember { mutableStateOf("") }
            val storageLocation = remember { mutableStateOf("") }
            val noteType = remember { mutableStateOf("") }

            Box(
                modifier = Modifier
                    .background(Primary200, MaterialTheme.shapes.large)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = "",
                        label = { Text(text = "Nama") },
                        onValueChange = { name.value = it},
                        modifier = Modifier
                            .background(Color.White, MaterialTheme.shapes.medium)
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = "",
                        label = { Text(text = "Jenis") },
                        onValueChange = { type.value = it},
                        modifier = Modifier
                            .background(Color.White, MaterialTheme.shapes.medium)
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = "",
                        label = { Text(text = "Berat (Kg)") },
                        onValueChange = { weight.value = it},
                        modifier = Modifier
                            .background(Color.White, MaterialTheme.shapes.medium)
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = "",
                        label = { Text(text = "Tanggal") },
                        onValueChange = { date.value = it},
                        modifier = Modifier
                            .background(Color.White, MaterialTheme.shapes.medium)
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = "",
                        label = { Text(text = "Lokasi Penyimpanan") },
                        onValueChange = { storageLocation.value = it},
                        modifier = Modifier
                            .background(Color.White, MaterialTheme.shapes.medium)
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = "",
                        label = { Text(text = "Catatan (Opsional)") },
                        onValueChange = { noteType.value = it},
                        modifier = Modifier
                            .background(Color.White, MaterialTheme.shapes.medium)
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = "",
                        label = { Text(text = "Masukkan Gambar") },
                        onValueChange = { noteType.value = it},
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Image,
                                contentDescription = "Icon Gambar",
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .background(Color.White, MaterialTheme.shapes.medium)
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .align(Alignment.End)
                    ) {
                        Button(
                            onClick = onNavigateUp,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEA4335),
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Batal")
                        }
                        Button(
                            onClick = onNavigateUp,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4285F4),
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Tambah")
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun AddNoteScreenPreview() {
    AmarineTheme {
        AddNoteContent(onNavigateUp = { /*TODO*/ })
    }
}