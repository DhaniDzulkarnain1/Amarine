package com.app.amarine.ui.screen.setting_about_app

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.settingAboutAppScreenRoute(navController: NavController) {
    composable(route = Screen.SettingAboutApp.route){
        SettingAboutAppScreen(navController)
    }
}