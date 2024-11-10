package com.app.amarine.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    val itemsPictureRes = listOf(
        R.drawable.ic_image_carousel_1,
        R.drawable.ic_image_carousel_2,
        R.drawable.ic_image_carousel_3,
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

@OptIn(ExperimentalPagerApi::class)
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
    val pagerState = rememberPagerState()

    // Automatic scrolling
    LaunchedEffect(pagerState) {
        while (true) {
            delay(25000L) // 25 detik
            val nextPage = (pagerState.currentPage + 1) % itemsPictureRes.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Amarine",
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .padding(top = 16.dp), // Menambahkan padding atas
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Bagian profil
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                    placeholder = { Text(text = "Ketik Disini...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            item {
                AnimatedVisibility(visible = searchQuery.isEmpty()) {
                    HorizontalPager(
                        count = itemsPictureRes.size,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 16.dp)
                    ) { page ->
                        Image(
                            painter = painterResource(id = itemsPictureRes[page]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.large)
                                .border(1.dp, TextFieldContainerColor, MaterialTheme.shapes.large)
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Panduan dan Artikel",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                        )
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
                R.drawable.ic_image_carousel_1,
                R.drawable.ic_image_carousel_2,
                R.drawable.ic_image_carousel_3,
            ),
            onQueryChange = {},
            articles = listOf(),
            onSeeMoreArticleClick = {},
            navigateToProfile = {}
        )
    }
}
