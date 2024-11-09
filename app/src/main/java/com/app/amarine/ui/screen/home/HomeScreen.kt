package com.app.amarine.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.R
import com.app.amarine.model.Article
import com.app.amarine.model.articles
import com.app.amarine.ui.components.ItemCardArticle
import com.app.amarine.ui.components.MySearchBar
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.AmarineTheme
import com.app.amarine.ui.theme.TextFieldContainerColor

@Composable
fun HomeScreen(navController: NavController) {
    var query by remember {
        mutableStateOf("")
    }
    val itemsPictureRes = listOf(
        R.drawable.image_home_1,
        R.drawable.image_home_2,
        R.drawable.image_home_3,
    )

    HomeContent(
        name = null,
        userImageUrl = null,
        searchQuery = query,
        itemsPictureRes = itemsPictureRes,
        articles = articles,
        onSeeMoreArticleClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set("article", it)
            navController.navigate(Screen.DetailArticle.route)
        },
        onQueryChange = { query = it },
        navigateToProfile = {
            navController.navigate(Screen.Profile.route)
        }
    )
}

@Composable
fun HomeContent(
    name: String?,
    userImageUrl: String?,
    searchQuery: String,
    itemsPictureRes: List<Int>,
    articles: List<Article>,
    navigateToProfile: () -> Unit,
    onSeeMoreArticleClick: (Article) -> Unit,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Amarine",
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Hi, ${name ?: "Guest"}!",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    AsyncImage(
                        model = userImageUrl ?: R.drawable.ic_user,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color(0xFFFFB16D), CircleShape)
                            .clickable(onClick = navigateToProfile)
                    )
                }
            }
            item {
                MySearchBar(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    placeholder = { Text(text = "Type here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            item {
                AnimatedVisibility(visible = searchQuery.isEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        items(items = itemsPictureRes) {
                            Image(
                                painter = painterResource(id = it),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(296.dp)
                                    .height(154.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .border(1.dp, TextFieldContainerColor, MaterialTheme.shapes.large)
                            )
                        }
                    }
                }
            }
            item {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Panduan dan Artikel",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            items(items = articles, key = { it.id }) {
                ItemCardArticle(
                    article = it,
                    onSeeMoreClick = { onSeeMoreArticleClick(it) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    AmarineTheme {
        HomeContent(
            name = null,
            userImageUrl = null,
            searchQuery = "",
            itemsPictureRes = listOf(
                R.drawable.image_home_1,
                R.drawable.image_home_2,
                R.drawable.image_home_3,
            ),
            onQueryChange = {},
            articles = listOf(),
            onSeeMoreArticleClick = {},
            navigateToProfile = {}
        )
    }
}