package com.app.amarine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.amarine.model.Member
import com.app.amarine.ui.theme.Primary200

@Composable
fun ItemCardMember(
    member: Member,
    onDetailMember: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable { onDetailMember() }
    ) {

        AsyncImage(
            model = member.imageResourceId,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 16.dp)
        ) {

            Text(
                text = member.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
            )

            Text(
                text = member.address,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = member.phoneNumber,
            )
        }

        Text(
            text = member.status,
            modifier = Modifier
                .background(Primary200, MaterialTheme.shapes.small)
                .padding(8.dp)
        )

    }

}