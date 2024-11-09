package com.app.amarine.ui.screen.report_problem

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.amarine.ui.navigation.Screen

fun NavGraphBuilder.reportProblemScreenRoute(navController: NavController) {
    composable(Screen.SettingReportProblem.route) {
        ReportProblemScreen(navController)
    }
}