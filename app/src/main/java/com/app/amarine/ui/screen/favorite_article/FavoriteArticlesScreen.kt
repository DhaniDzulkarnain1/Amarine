package com.app.amarine.ui.screen.favorite_article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.model.Article
import com.app.amarine.model.articles
import com.app.amarine.ui.components.ItemCardFavoriteArticle
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen

@Composable
fun FavoriteArticleScreen(
    navController: NavController
) {
    FavoriteArticleContent(
        favoriteArticles = articles,
        onNavigateUp = { navController.navigateUp()},
        onDetailArticle = {
            navController.currentBackStackEntry?.savedStateHandle?.set("article", it)
            navController.navigate(Screen.DetailArticle.route)
        }
    )
}
@Composable
fun FavoriteArticleContent(
    modifier: Modifier = Modifier,
    favoriteArticles: List<Article>,
    onDetailArticle: (Article) -> Unit,
    onNavigateUp: () -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Artikel Favorite",
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                items(items = favoriteArticles, key = { it.id }) {
                    ItemCardFavoriteArticle(
                        article = it,
                        onClick = { onDetailArticle(it) }
                    )
                }
            }
        }
    }
}

