package com.app.amarine.ui.screen.detail_note

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.R
import com.app.amarine.detail_note.DetailNoteViewModel
import com.app.amarine.model.Note
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.Primary200
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.time.Duration.Companion.seconds

private val CardBackground = Color(0xFFFFF3E0)

private fun String.formatDateTime(): String {
    return try {
        // Menggunakan format ISO 8601 untuk parsing input
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC") // Karena input dalam UTC (ditandai dengan Z)
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
fun DetailNoteScreen(
    noteId: Int,
    navController: NavController,
    viewModel: DetailNoteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val noteState by viewModel.noteState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val deleteNoteState by viewModel.deleteNoteState.collectAsState()
    var isShowDialog by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        viewModel.getDetailNote(noteId)
    }

    LaunchedEffect(deleteNoteState) {
        when (deleteNoteState) {
            is DetailNoteViewModel.Result.Success -> {
                isShowDialog = true
            }
            is DetailNoteViewModel.Result.Error -> {
                // Handle error
            }
            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>("refresh")?.let {
            if (it) {
                viewModel.getDetailNote(noteId)
                navController.currentBackStackEntry?.savedStateHandle?.remove<Boolean>("refresh")
            }
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = error ?: "Terjadi kesalahan",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewModel.getDetailNote(noteId) },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Coba Lagi")
                        }
                    }
                }
                else -> {
                    DetailNoteContent(
                        note = noteState,
                        onNavigateUp = { navController.navigateUp() },
                        onDelete = { viewModel.deleteNote(noteId) },
                        onEdit = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("note", it)
                            navController.navigate(Screen.EditNote.route)
                        }
                    )
                }
            }
        }
    }

    if (isShowDialog) {
        ResetSuccessDialog(
            onDismiss = {
                isShowDialog = false
                navController.navigate(Screen.Catatan.route) {
                    popUpTo(Screen.Catatan.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Composable
fun DetailNoteContent(
    note: Note?,
    onNavigateUp: () -> Unit,
    onDelete: () -> Unit,
    onEdit: (Note?) -> Unit,
    modifier: Modifier = Modifier
) {
    val showMenu = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Detail Catatan",
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu.value = true }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            tint = Color.White,
                            contentDescription = "Menu"
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
                .padding(horizontal = 16.dp)
        ) {
            Box(modifier = Modifier.align(Alignment.End)) {
                DropdownMenu(
                    expanded = showMenu.value,
                    onDismissRequest = { showMenu.value = false },
                    modifier = Modifier
                        .background(Primary200)
                        .padding(vertical = 8.dp)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Edit,
                                    contentDescription = "Edit",
                                    tint = Color.Black
                                )
                                Text(
                                    "Edit",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        },
                        onClick = {
                            showMenu.value = false
                            onEdit(note)
                        },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    DropdownMenuItem(
                        text = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Delete,
                                    contentDescription = "Hapus",
                                    tint = Color.Red
                                )
                                Text(
                                    "Hapus",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Red
                                    )
                                )
                            }
                        },
                        onClick = {
                            showMenu.value = false
                            showDeleteDialog.value = true
                        },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            DeleteItemDialog(
                showDialog = showDeleteDialog,
                onDeleteConfirmed = onDelete,
                onDismiss = { showDeleteDialog.value = false }
            )

            val imageUrl = if (!note?.gambar.isNullOrEmpty()) {
                "http://10.0.2.2:3000${note?.gambar}"
            } else {
                null
            }

            AsyncImage(
                model = imageUrl ?: R.drawable.ic_note_default,
                contentDescription = "Gambar ${note?.nama}",
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_note_default),
                placeholder = painterResource(id = R.drawable.ic_note_default),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.large)
                    .border(2.dp, Primary200, MaterialTheme.shapes.large)
            )

            Text(
                text = note?.nama ?: "-",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Box(
                modifier = Modifier
                    .background(CardBackground, MaterialTheme.shapes.large)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(20.dp)
                ) {
                    DetailItem(label = "Jenis", value = note?.jenis ?: "-")
                    DetailItem(label = "Berat", value = "${note?.berat} Kg")
                    DetailItem(label = "Tanggal", value = note?.tanggal?.formatDateTime() ?: "-")
                    DetailItem(label = "Waktu", value = note?.waktu?.formatTime() ?: "-")
                    DetailItem(label = "Lokasi Penyimpanan", value = note?.lokasi_penyimpanan ?: "-")
                    DetailItem(label = "Catatan", value = note?.catatan ?: "-", isMultiLine = true)
                }
            }
        }
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String,
    isMultiLine: Boolean = false,
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
            if (isMultiLine) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            } else {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

@Composable
fun DeleteItemDialog(
    showDialog: MutableState<Boolean>,
    onDeleteConfirmed: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            containerColor = Color.White,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = "Warning",
                    tint = Color.Red,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text(
                    text = "Konfirmasi Hapus Catatan",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Apakah Anda yakin?",
                        style = MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = "Catatan yang dihapus tidak dapat dikembalikan",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    )
                }
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onDismiss()
                            showDialog.value = false
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Gray
                        )
                    ) {
                        Text(
                            "Batal",
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = {
                            onDeleteConfirmed()
                            showDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Hapus",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun ResetSuccessDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismiss by rememberUpdatedState(newValue = onDismiss)
    LaunchedEffect(key1 = dismiss) {
        delay(2.seconds)
        onDismiss()
    }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                    contentDescription = "Success Icon",
                    modifier = Modifier.size(88.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Berhasil",
                    style = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Catatan Berhasil Dihapus",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 56.dp)
                )
            }
        }
    }
}