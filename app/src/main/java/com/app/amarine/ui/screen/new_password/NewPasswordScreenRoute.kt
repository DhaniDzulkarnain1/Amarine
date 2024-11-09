package com.app.amarine.ui.screen.new_password

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.newPasswordScreenRoute(navController: NavController) {
    composable(
        route = Screen.NewPassword.route,
    ) {
        NewPasswordScreen(navController = navController)
    }
}