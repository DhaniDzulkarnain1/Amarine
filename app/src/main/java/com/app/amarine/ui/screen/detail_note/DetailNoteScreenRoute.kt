package com.app.amarine.ui.screen.detail_note

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.model.Note
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.detailNoteScreenRoute(navController: NavController) {
    composable(Screen.DetailNote.route) {
        val note = navController.previousBackStackEntry?.savedStateHandle?.get<Note>("note")
        DetailNoteScreen(
            note,
            navController,
        )
    }
}