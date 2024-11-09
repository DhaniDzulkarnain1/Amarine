package com.app.amarine.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.amarine.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        actions = actions,
        modifier = modifier,
        expandedHeight = 56.dp,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Primary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
        )
    )
}