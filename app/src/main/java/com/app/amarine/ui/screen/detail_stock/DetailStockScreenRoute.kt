package com.app.amarine.ui.screen.detail_stock

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.model.Stock
import com.app.amarine.model.stocks
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.detailStockScreenRoute(navController: NavController) {
    composable(route = Screen.DetailStock.route) { backStackEntry ->
        // Ambil parameter stockId dari backStackEntry
        val stockId = backStackEntry.arguments?.getString("stockId")?.toIntOrNull()

        // Log untuk memverifikasi stockId
        Log.d("DetailStockScreenRoute", "Navigated with stockId: $stockId")

        // Cari data stok berdasarkan ID
        val stock = stocks.find { it.id == stockId }

        // Log untuk memverifikasi stok yang ditemukan
        Log.d("DetailStockScreenRoute", "Stock found: $stock")

        // Panggil DetailStockScreen dengan data stok
        DetailStockScreen(
            stock = stock,
            navController = navController
        )
    }
}
