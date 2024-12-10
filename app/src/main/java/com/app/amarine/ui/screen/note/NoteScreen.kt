package com.app.amarine.ui.screen.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.amarine.BuildConfig
import com.app.amarine.model.BaseResponse
import com.app.amarine.model.Note
import com.app.amarine.model.Result
import com.app.amarine.ui.components.ItemCardNote
import com.app.amarine.ui.components.MySearchBar
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    val notesState by viewModel.noteState.collectAsState()

    // Refresh data setiap kali screen dimuat
    LaunchedEffect(Unit) {
        viewModel.getNotes()
    }

    // Refresh data ketika kembali ke screen ini
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collectLatest { entry ->
            if (entry.destination.route == Screen.Catatan.route) {
                viewModel.getNotes()
            }
        }
    }

    NoteContent(
        notesState = notesState,
        searchQuery = query,
        onQueryChange = { query = it },
        onAddNote = { navController.navigate(Screen.AddNote.route) },
        onDetailClick = { note ->
            navController.navigate(Screen.DetailNote.createRoute(note.id))
        }
    )
}

@Composable
private fun NoteContent(
    notesState: Result<BaseResponse<List<Note>>>,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onAddNote: () -> Unit,
    onDetailClick: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Amarine",
                actions = {
                    IconButton(onClick = onAddNote) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        when (notesState) {
            is Result.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Result.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = notesState.message)
                }
            }
            is Result.Success -> {
                val notes = notesState.data.data ?: emptyList()
                LazyColumn(
                    modifier = Modifier
                        .padding(contentPadding)
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "Semua Hasil Tangkapan",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 8.dp)
                        )
                    }

                    item {
                        MySearchBar(
                            value = searchQuery,
                            onValueChange = onQueryChange,
                            placeholder = { Text(text = "Ketik Disini...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }

                    items(
                        items = notes,
                        key = { it.id }
                    ) { note ->
                        ItemCardNote(
                            note = note,
                            onDetailClick = { onDetailClick(note) },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
            is Result.Initial -> { }
        }
    }
}