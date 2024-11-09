package com.app.amarine.ui.screen.setting_profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.settingProfileRoute(navController: NavController) {
    composable(route = Screen.SettingProfile.route){
        SettingProfileScreen(navController)
    }
}