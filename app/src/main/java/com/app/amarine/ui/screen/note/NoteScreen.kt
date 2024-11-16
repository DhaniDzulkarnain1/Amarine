package com.app.amarine.ui.screen.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.model.Note
import com.app.amarine.model.notes
import com.app.amarine.ui.components.ItemCardNote
import com.app.amarine.ui.components.MySearchBar
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen

@Composable
fun NoteScreen(navController: NavController) {

    var query by remember {
        mutableStateOf("")
    }

    NoteContent(
        note = notes,
        searchQuery = query,
        onQueryChange = { query = it },
        onAddNote = { navController.navigate(Screen.AddNote.route) },
        onDetailClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set("note", it)
            navController.navigate(Screen.DetailNote.route)
        }
    )

}

@Composable
fun NoteContent(
    note: List<Note>,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onAddNote : () -> Unit,
    onDetailClick: (Note) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Amarine",
                actions = {
                    IconButton(
                        onClick = onAddNote
                    ) {
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

        LazyColumn(
            modifier = Modifier.padding(contentPadding),
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

            items(note, key = { it.id }) { notes ->
                ItemCardNote(
                    note = notes,
                    onDetailClick = { onDetailClick(notes) },
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
            }
        }
    }
}