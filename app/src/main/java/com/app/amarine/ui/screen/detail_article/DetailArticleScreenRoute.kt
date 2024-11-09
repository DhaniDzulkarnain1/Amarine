package com.app.amarine.ui.screen.detail_article

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.model.Article
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.detailArticleScreenRoute(navController: NavController) {
    composable(
        route = Screen.DetailArticle.route
    ) {
        val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
        DetailArticleScreen(article = article, navController = navController)
    }
}