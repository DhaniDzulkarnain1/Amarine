package com.app.amarine.ui.screen.detail_article

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.model.Article
import com.app.amarine.ui.components.MyTopAppBar

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
                            onFavoriteClick(!isFavorite)
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
            article?.description?.let {
                Text(
                    text = formatDescriptionText(it),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

fun formatDescriptionText(text: String): AnnotatedString {
    return buildAnnotatedString {
        text.split("\n").forEach { paragraph ->
            when {
                paragraph.startsWith("Pentingnya Pengawetan Ikan") ||
                        paragraph.startsWith("Metode Pengawetan Ikan") ||
                        paragraph.startsWith("Kesimpulan") ||
                        paragraph.startsWith("Jenis Alat Penangkap Ikan") ||
                        paragraph.startsWith("Alat Penangkap Ikan Tradisional") ||
                        paragraph.startsWith("Alat Penangkap Ikan Modern") ||
                        paragraph.startsWith("Perbedaan Antara Alat Tradisional dan Modern") ||
                        paragraph.startsWith("Komponen Sistem Perikanan") ||
                        paragraph.startsWith("A. Penanganan Ikan di Atas Kapal") ||
                        paragraph.startsWith("B. Penanganan Ikan di TPI (Tempat Pendaratan Ikan)") ||
                        paragraph.startsWith("Proses Pembongkaran Hasil Tangkapan") -> {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(paragraph)
                    }
                }
                else -> append(paragraph)
            }
            append("\n\n")
        }
    }
}
