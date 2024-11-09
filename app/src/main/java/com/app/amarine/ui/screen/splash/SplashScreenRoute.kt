package com.app.amarine.ui.screen.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.splashScreenRoute(navController: NavController) {
    composable(
        route = Screen.Splash.route,
    ) {
        SplashScreen(
            onTimeOut = {
                navController.navigate(Screen.OnBoarding.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}