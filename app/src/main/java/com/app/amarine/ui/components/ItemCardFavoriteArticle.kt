package com.app.amarine.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.amarine.model.Article
import com.app.amarine.ui.theme.Primary200

@Composable
fun ItemCardFavoriteArticle(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Primary200
        ),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
            .size(width = 160.dp, height = 240.dp)
            .clickable { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = article.imageResourceId,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp)
                )

                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(start = 8.dp)
            )

            Text(
                text = "Sumber : ${article.source}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 8.dp)
            )

        }
    }

}