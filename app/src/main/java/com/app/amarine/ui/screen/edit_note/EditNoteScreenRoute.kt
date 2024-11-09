package com.app.amarine.ui.screen.edit_note


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.model.Note
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.editNoteScreenRoute(navController: NavController) {
    val note = navController.previousBackStackEntry?.savedStateHandle?.get<Note>("note")
    composable(Screen.EditNote.route) {
        EditNoteScreen(
            note = note,
            navController = navController
        )
    }
}