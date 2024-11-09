package com.app.amarine.ui.screen.favorite_article

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.favoriteArticles(navController: NavController) {
    composable(route = Screen.FavoriteArticles.route) {
        FavoriteArticleScreen(navController)
    }
}