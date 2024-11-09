package com.app.amarine.ui.screen.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.profileScreenRoute(navController: NavController) {
    composable(
        route = Screen.Profile.route,
    ) {
        ProfileScreen(navController = navController)
    }
}