package com.app.amarine.ui.screen.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.loginScreenRoute(navController: NavController) {
    composable(
        route = Screen.Login.route,
    ) {
        LoginScreen(navController = navController)
    }
}