package com.app.amarine.ui.screen.edit_note

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.R
import com.app.amarine.edit_note.EditNoteViewModel
import com.app.amarine.model.Note
import com.app.amarine.model.Result
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.theme.Primary200
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private val CardBackground = Color(0xFFFFF3E0)
private val TextFieldBorder = Color(0xFF424242)
private val ErrorRed = Color(0xFFD32F2F)
private val BrandOrange = Color(0xFFFF5722)

private fun String.formatDateTime(): String {
    return try {
        // Menggunakan format ISO 8601 untuk parsing input
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val date = inputFormat.parse(this)

        // Format output tetap sama tapi menggunakan timezone lokal
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("id")).apply {
            timeZone = TimeZone.getDefault()
        }

        val result = date?.let { outputFormat.format(it) } ?: this
        Log.d("DEBUG_DATE", "Raw date: $this, Formatted date: $result")
        result
    } catch (e: Exception) {
        Log.e("DEBUG_DATE", "Error formatting date: $this", e)
        this
    }
}

private fun String.formatTime(): String {
    return try {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
        val time = inputFormat.parse(this)

        val outputFormat = SimpleDateFormat("HH:mm", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }

        val result = time?.let { outputFormat.format(it) } ?: this
        Log.d("DEBUG_TIME", "Raw time: $this, Formatted time: $result")
        result
    } catch (e: Exception) {
        Log.e("DEBUG_TIME", "Error formatting time: $this", e)
        this
    }
}

@Composable
fun EditNoteScreen(
    note: Note?,
    navController: NavController,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val editNoteState by viewModel.editNoteState.collectAsState()
    val imageUri by viewModel.imageUri.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("EditNote", "Selected image URI: $it")
            viewModel.updateImage(it)
        }
    }

    var nama by remember { mutableStateOf(note?.nama ?: "") }
    var jenis by remember { mutableStateOf(note?.jenis ?: "") }
    var berat by remember { mutableStateOf(note?.berat?.toString() ?: "") }
    var tanggal by remember { mutableStateOf(note?.tanggal?.formatDateTime() ?: "") }
    var waktu by remember { mutableStateOf(note?.waktu?.formatTime() ?: "") }
    var lokasiPenyimpanan by remember { mutableStateOf(note?.lokasi_penyimpanan ?: "") }
    var catatan by remember { mutableStateOf(note?.catatan ?: "") }

    LaunchedEffect(editNoteState) {
        when (editNoteState) {
            is Result.Success -> {
                Toast.makeText(context, "Berhasil mengupdate catatan", Toast.LENGTH_SHORT).show()
                navController.previousBackStackEntry?.savedStateHandle?.set("refresh", true)
                navController.popBackStack()
            }
            is Result.Error -> {
                Toast.makeText(context, (editNoteState as Result.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Edit Catatan",
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(16.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = when {
                                imageUri != null -> imageUri
                                !note?.gambar.isNullOrEmpty() -> "http://10.0.2.2:3000${note?.gambar}"
                                else -> R.drawable.ic_note_default
                            },
                            contentDescription = "Gambar ${note?.nama}",
                            error = painterResource(id = R.drawable.ic_note_default),
                            placeholder = painterResource(id = R.drawable.ic_note_default),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(MaterialTheme.shapes.large)
                                .border(2.dp, Primary200, MaterialTheme.shapes.large)
                        )

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.Black.copy(alpha = 0.2f))
                                .clickable { imagePickerLauncher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Ketuk untuk mengganti gambar",
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .background(
                                        Color.Black.copy(alpha = 0.5f),
                                        MaterialTheme.shapes.small
                                    )
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
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
                                value = nama,
                                onValueChange = { nama = it },
                                label = "Nama",
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )

                            EditFieldItem(
                                value = jenis,
                                onValueChange = { jenis = it },
                                label = "Jenis"
                            )

                            EditFieldItem(
                                value = berat,
                                onValueChange = { berat = it },
                                label = "Berat (Kg)",
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                            )

                            EditFieldItem(
                                value = tanggal,
                                onValueChange = { tanggal = it },
                                label = "Tanggal"
                            )

                            EditFieldItem(
                                value = waktu,
                                onValueChange = { waktu = it },
                                label = "Waktu"
                            )

                            EditFieldItem(
                                value = lokasiPenyimpanan,
                                onValueChange = { lokasiPenyimpanan = it },
                                label = "Lokasi Penyimpanan"
                            )

                            EditFieldItem(
                                value = catatan,
                                onValueChange = { catatan = it },
                                label = "Catatan"
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Button(
                                    onClick = { navController.navigateUp() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ErrorRed,
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text("Batal")
                                }
                                Button(
                                    onClick = {
                                        note?.id?.let { id ->
                                            viewModel.updateNote(
                                                id = id,
                                                nama = nama,
                                                jenis = jenis,
                                                berat = berat,
                                                tanggal = tanggal,
                                                waktu = waktu,
                                                lokasiPenyimpanan = lokasiPenyimpanan,
                                                catatan = catatan,
                                                imageUri = imageUri
                                            )
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = BrandOrange
                                    ),
                                    enabled = editNoteState !is Result.Loading
                                ) {
                                    Text("Simpan")
                                }
                            }
                        }
                    }
                }
            }

            if (editNoteState is Result.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun EditFieldItem(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
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
                keyboardOptions = keyboardOptions,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TextFieldBorder,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = TextFieldBorder,
                    unfocusedLabelColor = TextFieldBorder.copy(alpha = 0.7f)
                )
            )
        }
    }
}