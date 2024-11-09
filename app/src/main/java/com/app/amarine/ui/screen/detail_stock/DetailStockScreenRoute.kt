package com.app.amarine.ui.screen.detail_stock

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.model.Stock
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.detailStockScreenRoute(navController: NavController) {
    composable(route = Screen.DetailStock.route) {
        val stock = navController.previousBackStackEntry?.savedStateHandle?.get<Stock>("stock")
        DetailStockScreen(
            stock = stock,
            navController = navController
        )
    }
}