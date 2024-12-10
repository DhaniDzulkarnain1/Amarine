package com.app.amarine.ui.screen.add_note

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.BuildConfig
import com.app.amarine.R
import com.app.amarine.model.AddNoteFormState
import com.app.amarine.model.BaseResponse
import com.app.amarine.model.Note
import com.app.amarine.model.Result
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import java.util.*

private val BrandOrange = Color(0xFFFF5722)
private val CardBackground = Color(0xFFFFF3E0)
private val TextFieldBorder = Color(0xFF424242)
private val ErrorRed = Color(0xFFD32F2F)

@Composable
fun AddNoteScreen(
    navController: NavController,
    viewModel: AddNoteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val addNoteState by viewModel.addNoteState.collectAsState()

    LaunchedEffect(addNoteState) {
        if (addNoteState is Result.Success) {
            navController.navigate(Screen.Catatan.route) {
                popUpTo(Screen.AddNote.route) {
                    inclusive = true
                }
            }
        }
    }

    AddNoteContent(
        state = state,
        addNoteState = addNoteState,
        onEvent = viewModel::onEvent,
        onNavigateUp = { navController.navigateUp() }
    )
}

@Composable
private fun AddNoteContent(
    state: AddNoteFormState,
    addNoteState: Result<BaseResponse<Note>>,
    onEvent: (AddNoteEvent) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            onEvent(AddNoteEvent.OnDateChange(
                String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day)
            ))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            onEvent(AddNoteEvent.OnTimeChange(
                String.format(Locale.US, "%02d:%02d", hour, minute)
            ))
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraPermissionGranted = permissions[Manifest.permission.CAMERA] ?: false
        val readPermissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions[Manifest.permission.READ_MEDIA_IMAGES] ?: false
        } else {
            permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
        }

        if (cameraPermissionGranted && readPermissionGranted) {
            onEvent(AddNoteEvent.OnShowImagePicker(true))
        } else {
            onEvent(AddNoteEvent.OnShowPermissionDialog(true))
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onEvent(AddNoteEvent.OnImageSelected(uri))
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            state.photoUri?.let { uri ->
                onEvent(AddNoteEvent.OnImageSelected(uri))
            }
        }
    }

    fun checkAndRequestPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val readPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val permissionsToRequest = mutableListOf<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }
        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionsToRequest.add(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        if (permissionsToRequest.isEmpty()) {
            onEvent(AddNoteEvent.OnShowImagePicker(true))
        } else {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    fun createImageUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }

    if (state.showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { onEvent(AddNoteEvent.OnShowPermissionDialog(false)) },
            title = { Text("Izin Diperlukan") },
            text = { Text("Aplikasi memerlukan izin untuk mengakses kamera dan penyimpanan.") },
            confirmButton = {
                TextButton(onClick = { onEvent(AddNoteEvent.OnShowPermissionDialog(false)) }) {
                    Text("OK")
                }
            }
        )
    }

    if (state.showImagePickerDialog) {
        AlertDialog(
            onDismissRequest = { onEvent(AddNoteEvent.OnShowImagePicker(false)) },
            title = { Text("Pilih Sumber Gambar") },
            text = { Text("Silakan pilih sumber gambar") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val photoUri = createImageUri()
                        onEvent(AddNoteEvent.OnPhotoUriChange(photoUri))
                        photoUri?.let { cameraLauncher.launch(it) }
                        onEvent(AddNoteEvent.OnShowImagePicker(false))
                    }
                ) {
                    Text("Kamera")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        galleryLauncher.launch("image/*")
                        onEvent(AddNoteEvent.OnShowImagePicker(false))
                    }
                ) {
                    Text("Galeri")
                }
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Tambah Catatan",
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (addNoteState is Result.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(CardBackground, MaterialTheme.shapes.large)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = state.name,
                            onValueChange = { onEvent(AddNoteEvent.OnNameChange(it)) },
                            label = { Text("Nama") },
                            isError = state.errors.contains("name"),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (state.errors.contains("name")) {
                            Text(
                                text = "Nama tidak boleh kosong",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        OutlinedTextField(
                            value = state.type,
                            onValueChange = { onEvent(AddNoteEvent.OnTypeChange(it)) },
                            label = { Text("Jenis") },
                            isError = state.errors.contains("type"),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (state.errors.contains("type")) {
                            Text(
                                text = "Jenis tidak boleh kosong",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        OutlinedTextField(
                            value = state.weight,
                            onValueChange = { onEvent(AddNoteEvent.OnWeightChange(it)) },
                            label = { Text("Berat (Kg)") },
                            isError = state.errors.contains("weight"),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (state.errors.contains("weight")) {
                            Text(
                                text = if (state.weight.isEmpty()) "Berat tidak boleh kosong"
                                else "Masukkan berat yang valid",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = state.date,
                                onValueChange = { },
                                label = { Text("Tanggal") },
                                readOnly = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { datePickerDialog.show() },
                                isError = state.errors.contains("date"),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Pilih Tanggal",
                                        modifier = Modifier.clickable { datePickerDialog.show() }
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = state.time,
                                onValueChange = { },
                                label = { Text("Waktu") },
                                readOnly = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { timePickerDialog.show() },
                                isError = state.errors.contains("time"),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = "Pilih Waktu",
                                        modifier = Modifier.clickable { timePickerDialog.show() }
                                    )
                                }
                            )
                        }

                        OutlinedTextField(
                            value = state.storageLocation,
                            onValueChange = { onEvent(AddNoteEvent.OnStorageLocationChange(it)) },
                            label = { Text("Lokasi Penyimpanan") },
                            isError = state.errors.contains("storageLocation"),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (state.errors.contains("storageLocation")) {
                            Text(
                                text = "Lokasi penyimpanan tidak boleh kosong",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        OutlinedTextField(
                            value = state.note,
                            onValueChange = { onEvent(AddNoteEvent.OnNoteChange(it)) },
                            label = { Text("Catatan (Opsional)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Column {
                            OutlinedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clickable { checkAndRequestPermissions() }
                                    .border(
                                        width = if (state.errors.contains("image")) 1.dp else 0.dp,
                                        color = if (state.errors.contains("image"))
                                            MaterialTheme.colorScheme.error
                                        else Color.Transparent,
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.White)
                                ) {
                                    if (state.selectedImageUri != null) {
                                        AsyncImage(
                                            model = state.selectedImageUri, // Hapus BuildConfig.BASE_URL
                                            contentDescription = "Selected Image",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AddPhotoAlternate,
                                                contentDescription = "Upload Gambar",
                                                tint = if (state.errors.contains("image"))
                                                    MaterialTheme.colorScheme.error
                                                else TextFieldBorder,
                                                modifier = Modifier.size(36.dp)
                                            )
                                            Text(
                                                "Tambah Foto",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = if (state.errors.contains("image"))
                                                    MaterialTheme.colorScheme.error
                                                else TextFieldBorder,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(top = 8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            if (state.errors.contains("image")) {
                                Text(
                                    text = "Gambar tidak boleh kosong",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledTonalButton(
                        onClick = onNavigateUp,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = ErrorRed,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text(
                            "Batal",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    FilledTonalButton(
                        onClick = { onEvent(AddNoteEvent.OnSubmit(context)) },
                        enabled = addNoteState !is Result.Loading,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = BrandOrange,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text(
                            "Tambah",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}