package com.app.amarine.ui.screen.setting_notification

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.settingNotificationRoute(navController: NavController) {
    composable(Screen.SettingNotification.route) {
        SettingNotificationScreen(navController)
    }
}