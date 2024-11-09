package com.app.amarine.ui.screen.otp_email

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.otpScreenRoute(navController: NavController) {
    composable(
        route = Screen.OTPEmail.route
    ) {
        OTPScreen(
            navController = navController
        )
    }
}