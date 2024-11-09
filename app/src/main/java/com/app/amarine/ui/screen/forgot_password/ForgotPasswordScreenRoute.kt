package com.app.amarine.ui.screen.forgot_password

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.forgotPasswordScreenRoute(navController: NavController) {
    composable(
        route = Screen.ForgotPassword.route
    ) {
        ForgotPasswordScreen(navController)
    }
}