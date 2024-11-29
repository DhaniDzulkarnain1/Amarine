package com.app.amarine.ui.screen.detail_note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.R
import com.app.amarine.model.Note
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.screen.detail_member.DetailMemberContent
import com.app.amarine.ui.theme.AmarineTheme
import com.app.amarine.ui.theme.Primary200
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

private val CardBackground = Color(0xFFFFF3E0)

@Composable
fun DetailNoteScreen(
    note: Note?,
    navController: NavController
) {
    var isShowDialog by remember {
        mutableStateOf(false)
    }

    DetailNoteContent(
        note = note,
        onNavigateUp = { navController.navigateUp() },
        onDelete = { isShowDialog = true },
        onEdit = {
            navController.currentBackStackEntry?.savedStateHandle?.set("note", it)
            navController.navigate(Screen.EditNote.route)
        }
    )

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

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Detail Catatan",
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showMenu.value = true }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            tint = Color.White,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        val showDeleteDialog = remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(contentPadding)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier.align(Alignment.End)
            ) {
                DropdownMenu(
                    expanded = showMenu.value,
                    onDismissRequest = { showMenu.value = false },
                    modifier = Modifier.background(Primary200)
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = { onEdit(note) }
                    )

                    DropdownMenuItem(
                        text = { Text("Hapus") },
                        onClick = { showDeleteDialog.value = true }
                    )
                }
            }

            DeleteItemDialog(
                showDialog = showDeleteDialog,
                onDeleteConfirmed = onDelete,
            ) { }

            AsyncImage(
                model = note?.imageResourceId,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(MaterialTheme.shapes.large)
                    .border(2.dp, Primary200, MaterialTheme.shapes.large)
            )

            Text(
                text = note?.name.toString(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
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
                    DetailItem(label = "Jenis", value = note?.type ?: "-")
                    DetailItem(label = "Berat", value = "${note?.weight} Kg")
                    DetailItem(label = "Tanggal", value = note?.date ?: "-")
                    DetailItem(label = "Lokasi Penyimpanan", value = note?.storageLocation ?: "-")
                    DetailItem(label = "Catatan", value = note?.note ?: "-", isMultiLine = true)
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
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            if (isMultiLine) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            } else {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
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
            title = {
                Text(text = "Konfirmasi Hapus Catatan")
            },
            text = {
                Text(text = "Apakah Anda Yakin Ingin Menghapus Catatan Penjualan?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteConfirmed()
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(Primary200)
                ) {
                    Text("Hapus Catatan")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDismiss()
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(Primary200)
                ) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun ResetSuccessDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
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
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                    contentDescription = null,
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
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 56.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailMemberContentPreview() {
    AmarineTheme {
        DetailMemberContent(member = null, onNavigateUp = { /*TODO*/ })
    }
}