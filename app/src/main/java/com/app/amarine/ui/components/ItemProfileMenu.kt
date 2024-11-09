package com.app.amarine.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ItemProfileMenu(
    title: @Composable RowScope.() -> Unit,
    iconStart: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    iconEnd: @Composable (() -> Unit)? = null,
    rowType: RowType = RowType.MIDDLE,
    onClick: () -> Unit,
) {
    val shape = when (rowType) {
        RowType.TOP -> {
            RoundedCornerShape(16.dp, 16.dp)
        }
        RowType.BOTTOM -> {
            RoundedCornerShape(
                0.dp,
                0.dp,
                16.dp,
                16.dp)
        }
        RowType.SINGLE -> {
            RoundedCornerShape(
                16.dp,
                16.dp,
                16.dp,
                16.dp)
        }
        else -> {
            RoundedCornerShape(0.dp)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(shape)
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        iconStart()
        Spacer(modifier = Modifier.width(16.dp))
        title()
        if (iconEnd != null) {
            iconEnd()
        }
    }
}

enum class RowType {
    TOP, MIDDLE, BOTTOM, SINGLE
}