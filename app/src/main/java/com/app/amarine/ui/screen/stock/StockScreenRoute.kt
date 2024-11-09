package com.app.amarine.ui.screen.stock

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.stockScreenRoute(navController: NavController) {
    composable(Screen.Stock.route) {
        StockScreen(navController)
    }
}