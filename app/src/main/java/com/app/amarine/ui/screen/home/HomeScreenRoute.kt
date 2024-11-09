package com.app.amarine.ui.screen.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.homeScreenRoute(navController: NavController) {
    composable(
        route = Screen.Home.route,
    ) {
        HomeScreen(navController = navController)
    }
}