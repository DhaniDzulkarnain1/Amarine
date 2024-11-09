package com.app.amarine.ui.screen.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.onBoardingScreenRoute(navController: NavController) {
    composable(
        route = Screen.OnBoarding.route,
    ) {
        OnBoardingScreen(navController)
    }
}