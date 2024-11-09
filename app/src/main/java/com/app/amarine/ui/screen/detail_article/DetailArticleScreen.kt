package com.app.amarine.ui.screen.detail_article

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.model.Article
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.screen.add_note.AddNoteContent
import com.app.amarine.ui.screen.detail_note.DetailNoteContent
import com.app.amarine.ui.theme.AmarineTheme

@Composable
fun DetailArticleScreen(
    article: Article?,
    navController: NavController,
) {
    DetailArticleContent(
        article = article,
        onNavigateUp = { navController.navigateUp() },
        onFavoriteClick = { newState ->
            article?.isFavorite = newState
        }
    )
}

@Composable
fun DetailArticleContent(
    article: Article?,
    onNavigateUp: () -> Unit,
    onFavoriteClick: (newState: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFavorite by rememberSaveable {
        mutableStateOf(article?.isFavorite ?: false)
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "",
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onFavoriteClick(!article!!.isFavorite)
                            isFavorite = !isFavorite
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) Color.Red else Color.White,
                            modifier = Modifier
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = article?.title ?: "",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sumber : ${article?.source ?: ""}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = article?.imageResourceId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = article?.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun DetailNoteContentPreview() {
    AmarineTheme {
        DetailNoteContent(
            note = null,
            onNavigateUp = { /*TODO*/ },
            onDelete = { /*TODO*/ },
            onEdit = {}
        )
    }
}