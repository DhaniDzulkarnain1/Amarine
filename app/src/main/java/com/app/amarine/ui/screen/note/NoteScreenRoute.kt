package com.app.amarine.ui.screen.note

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.noteScreenRoute(navController: NavController) {
    composable(route = Screen.Catatan.route) {
        NoteScreen(navController)
    }
}