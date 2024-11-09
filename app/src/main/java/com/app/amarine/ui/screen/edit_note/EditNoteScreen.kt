package com.app.amarine.ui.screen.edit_note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.model.Note
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.screen.detail_note.DeleteItemDialog
import com.app.amarine.ui.theme.Primary200

@Composable
fun EditNoteScreen(
    note: Note?,
    navController: NavController
) {

    EditNoteContent(
        note = note,
        onNavigateUp = { navController.navigateUp() },
        onSave = { navController.navigate(Screen.Catatan.route)},
        onCancel = { navController.navigateUp() }
    )

}

@Composable
fun EditNoteContent(
    note: Note?,
    onNavigateUp : () -> Unit,
    onSave : () -> Unit,
    onCancel : () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Edit Catatan",
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

        val name = remember { mutableStateOf("") }
        val type = remember { mutableStateOf("") }
        val weight = remember { mutableStateOf("") }
        val date = remember { mutableStateOf("") }
        val storageLocation = remember { mutableStateOf("") }
        val noteType = remember { mutableStateOf("") }

        LazyColumn (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
        ) {

            item {
                AsyncImage(
                    model = note?.imageResourceId,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(MaterialTheme.shapes.large)
                        .border(2.dp, Primary200, MaterialTheme.shapes.large)
                )
            }

            item {
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
                            value = name.value,
                            onValueChange = { name.value = it },
                            placeholder = { Text("Nama : ${note?.name}") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            ),
                            modifier = Modifier
                                .background(Color.White, MaterialTheme.shapes.medium)
                                .padding(8.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = type.value,
                            onValueChange = { type.value = it},
                            placeholder = { Text("Jenis : ${note?.type}") },
                            modifier = Modifier
                                .background(Color.White, MaterialTheme.shapes.medium)
                                .padding(8.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = weight.value,
                            onValueChange = { weight.value = it},
                            placeholder = { Text("Berat : ${note?.weight}") },
                            modifier = Modifier
                                .background(Color.White, MaterialTheme.shapes.medium)
                                .padding(8.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = date.value,
                            onValueChange = { date.value = it},
                            placeholder = { Text("Tanggal : ${note?.date}") },
                            modifier = Modifier
                                .background(Color.White, MaterialTheme.shapes.medium)
                                .padding(8.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = storageLocation.value,
                            onValueChange = { storageLocation.value = it},
                            placeholder = { Text("Lokasi Penyimpanan : ${note?.storageLocation}") },
                            modifier = Modifier
                                .background(Color.White, MaterialTheme.shapes.medium)
                                .padding(8.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = noteType.value,
                            onValueChange = { noteType.value = it},
                            placeholder = { Text("Catatan : ${note?.note}") },
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
                                onClick = onCancel
                            ) {
                                Text(text = "Batal")
                            }
                            Button(
                                onClick = onSave
                            ) {
                                Text(text = "Simpan")
                            }
                        }
                    }
                }
            }
        }
    }
}