package com.app.amarine.ui.screen.register

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.registerScreenRoute(navController: NavController) {
    composable(
        route = Screen.Register.route
    ) {
        RegisterScreen(navController)
    }
}