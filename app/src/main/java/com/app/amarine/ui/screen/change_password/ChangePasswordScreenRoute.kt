package com.app.amarine.ui.screen.change_password

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.changePasswordScreenRoute(navController: NavController) {
    composable(Screen.ChangePassword.route) {
        ChangePasswordScreen(navController)
    }
}