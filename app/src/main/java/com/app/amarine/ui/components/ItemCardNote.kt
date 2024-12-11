package com.app.amarine.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.amarine.R
import com.app.amarine.model.Note
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private fun String.formatDateTime(): String {
    return try {
        // Menggunakan format ISO 8601 untuk parsing input
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC") // Karena input dalam UTC (ditandai dengan Z)
        }
        val date = inputFormat.parse(this)

        // Format output tetap sama tapi menggunakan timezone lokal
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("id")).apply {
            timeZone = TimeZone.getDefault()
        }

        val result = date?.let { outputFormat.format(it) } ?: this
        Log.d("DEBUG_DATE", "Raw date: $this, Formatted date: $result")
        result
    } catch (e: Exception) {
        Log.e("DEBUG_DATE", "Error formatting date: $this", e)
        this
    }
}

private fun String.formatTime(): String {
    return try {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
        val time = inputFormat.parse(this)

        val outputFormat = SimpleDateFormat("HH:mm", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }

        val result = time?.let { outputFormat.format(it) } ?: this
        Log.d("DEBUG_TIME", "Raw time: $this, Formatted time: $result")
        result
    } catch (e: Exception) {
        Log.e("DEBUG_TIME", "Error formatting time: $this", e)
        this
    }
}

@Composable
fun ItemCardNote(
    note: Note,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onDetailClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF3E0)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Box {
                val imageUrl = if (!note.gambar.isNullOrEmpty()) {
                    "http://10.0.2.2:3000${note.gambar}"
                } else {
                    null
                }
                Log.d("ImageDebug", "Raw gambar path: ${note.gambar}")
                Log.d("ImageDebug", "Final URL: $imageUrl")

                AsyncImage(
                    model = imageUrl ?: R.drawable.ic_note_default,
                    contentDescription = "Gambar ${note.nama}",
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_note_default),
                    placeholder = painterResource(id = R.drawable.ic_note_default),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(MaterialTheme.shapes.medium)
                )

                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .width(64.dp)
                        .height(32.dp)
                        .align(Alignment.BottomStart)
                        .background(
                            Color(0xFFFF7043).copy(alpha = 0.9f),
                            MaterialTheme.shapes.small
                        )
                ) {
                    Text(
                        text = "${note.berat} Kg",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = note.nama,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(
                    text = note.tanggal.formatDateTime(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(0.dp))
            }

            note.catatan?.let { catatan ->
                if (catatan.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}