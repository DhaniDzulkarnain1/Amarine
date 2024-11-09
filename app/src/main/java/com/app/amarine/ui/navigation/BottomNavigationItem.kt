package com.app.amarine.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen,
)
