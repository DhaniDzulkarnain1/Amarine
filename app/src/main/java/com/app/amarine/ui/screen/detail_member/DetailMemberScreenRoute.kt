package com.app.amarine.ui.screen.detail_member

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.model.Member
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.detailMemberScreenRoute(navController: NavController) {

    val member = navController.previousBackStackEntry?.savedStateHandle?.get<Member>("member")
    composable(Screen.DetailAnggota.route) {
        DetailMemberScreen(
            member = member,
            navController = navController
        )
    }
}