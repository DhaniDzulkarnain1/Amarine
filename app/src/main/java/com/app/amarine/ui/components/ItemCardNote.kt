package com.app.amarine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.amarine.model.Note
import com.app.amarine.ui.theme.Primary200

@Composable
fun ItemCardNote(
    note: Note,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .clickable { onDetailClick() }
    ) {

        Box {
            AsyncImage(
                model = note.imageResourceId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large)
                    .border(2.dp, Primary200, MaterialTheme.shapes.large)

            )
            Box(
                modifier = Modifier
                    .width(72.dp)
                    .height(56.dp)
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .background(Primary200, MaterialTheme.shapes.medium)
            ){
                Text(
                    text = note.weight,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

        Text(
            text = "${note.name} | ${note.date}"
        )

        Text(
            text = "Tersedia",
            color = Color.Blue
        )

    }
}