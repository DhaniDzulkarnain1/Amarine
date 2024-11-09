package com.app.amarine.ui.screen.reset_password

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.resetPasswordScreenRoute(navController: NavController) {
    composable(route = Screen.ResetPassword.route) {
        ResetPasswordScreen(navController)
    }
}