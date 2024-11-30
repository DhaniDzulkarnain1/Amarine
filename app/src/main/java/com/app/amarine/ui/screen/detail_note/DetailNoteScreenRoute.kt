package com.app.amarine.ui.screen.detail_note

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.amarine.model.notes
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.detailNoteScreenRoute(navController: NavController) {
    composable(
        route = Screen.DetailNote.route,
        arguments = listOf(navArgument("noteId") { type = NavType.IntType })
    ) { backStackEntry ->
        val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
        val note = notes.find { it.id == noteId }
        DetailNoteScreen(note = note, navController = navController)
    }
}