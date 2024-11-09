package com.app.amarine.ui.screen.add_note

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.addNoteScreenRoute(navController: NavController) {
    composable(Screen.AddNote.route) {
        AddNoteScreen(navController = navController)
    }
}