package com.app.amarine.ui.screen.detail_stock

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.detailStockScreenRoute(navController: NavController) {
    composable(
        route = Screen.DetailStock.route,
        arguments = listOf(
            navArgument("stockName") {  // Ubah dari stockId ke stockName
                type = NavType.StringType  // Ubah tipe ke String
            }
        )
    ) { backStackEntry ->
        val stockName = backStackEntry.arguments?.getString("stockName") ?: ""  // Ambil nama stok

        DetailStockScreen(
            stockName = stockName,  // Kirim nama stok
            navController = navController
        )
    }
}