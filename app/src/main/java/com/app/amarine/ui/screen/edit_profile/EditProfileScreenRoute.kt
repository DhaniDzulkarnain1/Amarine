package com.app.amarine.ui.screen.edit_profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.editProfileScreen(navController: NavController) {
    composable(route = Screen.EditProfile.route) {
        EditProfileScreen(navController)
    }
}