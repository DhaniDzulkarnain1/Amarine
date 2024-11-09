package com.app.amarine.ui.screen.member

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.memberScreenRoute(navController: NavController) {
    composable(Screen.Anggota.route) {
        MemberScreen(navController)
    }
}