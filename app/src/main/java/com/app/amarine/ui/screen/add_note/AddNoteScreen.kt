package com.app.amarine.ui.screen.add_note

import android.Manifest
import android.app.DatePickerDialog
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.theme.AmarineTheme
import java.util.*

private val BrandOrange = Color(0xFFFF5722)
private val CardBackground = Color(0xFFFFF3E0)
private val TextFieldBorder = Color(0xFF424242)
private val ErrorRed = Color(0xFFD32F2F)

@Composable
fun AddNoteScreen(navController: NavController) {
    AddNoteContent(onNavigateUp = { navController.navigateUp() })
}

@Composable
fun AddNoteContent(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    // States untuk form
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var storageLocation by remember { mutableStateOf("") }
    var noteType by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // States untuk dialog
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    // States untuk error
    var nameError by remember { mutableStateOf(false) }
    var typeError by remember { mutableStateOf(false) }
    var weightError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }
    var storageLocationError by remember { mutableStateOf(false) }
    var imageError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Permission launcher
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
            showImagePickerDialog = true
        } else {
            showPermissionDialog = true
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        imageError = uri == null
    }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedImageUri = photoUri
            imageError = false
        }
    }

    // Date picker dialog
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            android.R.style.Theme_DeviceDefault_Light_Dialog,
            { _, year, month, dayOfMonth ->
                date = "$dayOfMonth/${month + 1}/$year"
                dateError = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnShowListener {
                getButton(DatePickerDialog.BUTTON_POSITIVE)?.setTextColor(BrandOrange.hashCode())
                getButton(DatePickerDialog.BUTTON_NEGATIVE)?.setTextColor(Color.Gray.hashCode())
            }
        }
    }

    // Function to check and request permissions
    fun checkAndRequestPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
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
            showImagePickerDialog = true
        } else {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    // Function to create image URI for camera
    fun createImageUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }

    // Permission Dialog
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Izin Diperlukan") },
            text = { Text("Aplikasi memerlukan izin untuk mengakses kamera dan penyimpanan.") },
            confirmButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    // Image Picker Dialog
    if (showImagePickerDialog) {
        AlertDialog(
            onDismissRequest = { showImagePickerDialog = false },
            title = { Text("Pilih Sumber Gambar") },
            text = { Text("Silakan pilih sumber gambar") },
            confirmButton = {
                TextButton(
                    onClick = {
                        photoUri = createImageUri()
                        photoUri?.let { cameraLauncher.launch(it) }
                        showImagePickerDialog = false
                    }
                ) {
                    Text("Kamera")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        galleryLauncher.launch("image/*")
                        showImagePickerDialog = false
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
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
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
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    CustomTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = it.isEmpty()
                        },
                        label = "Nama",
                        isError = nameError,
                        errorMessage = "Nama tidak boleh kosong"
                    )

                    CustomTextField(
                        value = type,
                        onValueChange = {
                            type = it
                            typeError = it.isEmpty()
                        },
                        label = "Jenis",
                        isError = typeError,
                        errorMessage = "Jenis tidak boleh kosong"
                    )

                    CustomTextField(
                        value = weight,
                        onValueChange = {
                            weight = it
                            weightError = weight.isEmpty() || !weight.matches(Regex("^\\d*\\.?\\d*$"))
                        },
                        label = "Berat (Kg)",
                        isError = weightError,
                        errorMessage = if (weight.isEmpty()) "Berat tidak boleh kosong"
                        else if (!weight.matches(Regex("^\\d*\\.?\\d*$"))) "Masukkan berat yang valid"
                        else null
                    )

                    Column {
                        OutlinedTextField(
                            value = date,
                            onValueChange = {},
                            label = { Text("Tanggal") },
                            readOnly = true,
                            isError = dateError,
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialog.show() }) {
                                    Icon(Icons.Default.CalendarToday, "Pilih Tanggal")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.medium)
                                .background(Color.White)
                                .border(
                                    width = 0.5.dp,
                                    color = if (dateError) MaterialTheme.colorScheme.error
                                    else TextFieldBorder.copy(alpha = 0.3f),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .clickable { datePickerDialog.show() },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (dateError) MaterialTheme.colorScheme.error else TextFieldBorder,
                                unfocusedBorderColor = Color.Transparent,
                                focusedLabelColor = TextFieldBorder,
                                unfocusedLabelColor = TextFieldBorder.copy(alpha = 0.7f)
                            )
                        )
                        if (dateError) {
                            Text(
                                text = "Tanggal tidak boleh kosong",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }
                    }

                    CustomTextField(
                        value = storageLocation,
                        onValueChange = {
                            storageLocation = it
                            storageLocationError = it.isEmpty()
                        },
                        label = "Lokasi Penyimpanan",
                        isError = storageLocationError,
                        errorMessage = "Lokasi penyimpanan tidak boleh kosong"
                    )

                    CustomTextField(
                        value = noteType,
                        onValueChange = { noteType = it },
                        label = "Catatan (Opsional)"
                    )

                    Column {
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clickable { checkAndRequestPermissions() }
                                .border(
                                    width = if (imageError) 1.dp else 0.dp,
                                    color = if (imageError) MaterialTheme.colorScheme.error
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
                                if (selectedImageUri != null) {
                                    AsyncImage(
                                        model = selectedImageUri,
                                        contentDescription = "Selected Image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AddPhotoAlternate,
                                            contentDescription = "Upload Gambar",
                                            tint = if (imageError) MaterialTheme.colorScheme.error else TextFieldBorder,
                                            modifier = Modifier.size(36.dp)
                                        )
                                        Text(
                                            "Tambah Foto",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (imageError) MaterialTheme.colorScheme.error else TextFieldBorder,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(top = 8.dp)
                                        )

                                    }
                                }
                            }
                        }
                        if (imageError) {
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
                    onClick = {
                        nameError = name.isEmpty()
                        typeError = type.isEmpty()
                        weightError = weight.isEmpty() || !weight.matches(Regex("^\\d*\\.?\\d*$"))
                        dateError = date.isEmpty()
                        storageLocationError = storageLocation.isEmpty()
                        imageError = selectedImageUri == null

                        if (!nameError && !typeError && !weightError && !dateError &&
                            !storageLocationError && !imageError) {
                            onNavigateUp()
                        }
                    },
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

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White)
                .border(
                    width = 0.5.dp,
                    color = when {
                        isError -> MaterialTheme.colorScheme.error
                        else -> TextFieldBorder.copy(alpha = 0.3f)
                    },
                    shape = MaterialTheme.shapes.medium
                ),
            isError = isError,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else TextFieldBorder,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = TextFieldBorder,
                unfocusedLabelColor = TextFieldBorder.copy(alpha = 0.7f)
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddNoteScreenPreview() {
    AmarineTheme {
        AddNoteContent(onNavigateUp = {})
    }
}