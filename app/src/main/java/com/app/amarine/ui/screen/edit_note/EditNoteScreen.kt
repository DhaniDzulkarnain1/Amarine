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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.model.Note
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.Primary200

private val CardBackground = Color(0xFFFFF3E0)
private val TextFieldBorder = Color(0xFF424242)
private val ErrorRed = Color(0xFFD32F2F)
private val BrandOrange = Color(0xFFFF5722)

@Composable
fun EditNoteScreen(note: Note?, navController: NavController) {
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

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
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
                        .background(CardBackground, MaterialTheme.shapes.large)
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(20.dp)
                    ) {
                        EditFieldItem(
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = "Nama",
                            placeholder = note?.name ?: "",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        EditFieldItem(
                            value = type.value,
                            onValueChange = { type.value = it },
                            label = "Jenis",
                            placeholder = note?.type ?: ""
                        )

                        EditFieldItem(
                            value = weight.value,
                            onValueChange = { weight.value = it },
                            label = "Berat",
                            placeholder = "${note?.weight} Kg"
                        )

                        EditFieldItem(
                            value = date.value,
                            onValueChange = { date.value = it },
                            label = "Tanggal",
                            placeholder = note?.date ?: ""
                        )

                        EditFieldItem(
                            value = storageLocation.value,
                            onValueChange = { storageLocation.value = it },
                            label = "Lokasi Penyimpanan",
                            placeholder = note?.storageLocation ?: ""
                        )

                        EditFieldItem(
                            value = noteType.value,
                            onValueChange = { noteType.value = it },
                            label = "Catatan",
                            placeholder = note?.note ?: ""
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Button(
                                onClick = onCancel,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ErrorRed,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Batal")
                            }
                            Button(
                                onClick = onSave,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BrandOrange
                                )
                            ) {
                                Text("Simpan")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EditFieldItem(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder) },
                keyboardOptions = keyboardOptions,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TextFieldBorder,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }
    }
}