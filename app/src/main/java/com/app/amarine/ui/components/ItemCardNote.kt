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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.amarine.R
import com.app.amarine.model.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun getTimeAgo(dateString: String, timeString: String): String {
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val currentDate = Date()
        val date = dateFormat.parse("$dateString $timeString")

        if (date != null) {
            val diffInMillis = currentDate.time - date.time
            val diffInSeconds = diffInMillis / 1000
            val diffInMinutes = diffInSeconds / 60
            val diffInHours = diffInMinutes / 60
            val diffInDays = diffInHours / 24

            return when {
                diffInSeconds < 60 -> "Baru saja"
                diffInMinutes < 60 -> "${diffInMinutes.toInt()} menit yang lalu"
                diffInHours < 24 -> "${diffInHours.toInt()} jam yang lalu"
                diffInDays == 1L -> "Kemarin"
                diffInDays < 7 -> "$diffInDays hari yang lalu"
                else -> {
                    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("id"))
                    outputFormat.format(date)
                }
            }
        }
    } catch (e: Exception) {
        Log.e("TimeAgo", "Error calculating time ago", e)
    }
    return "Baru saja" // default fallback
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.nama,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = getTimeAgo(note.tanggal, note.waktu),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}