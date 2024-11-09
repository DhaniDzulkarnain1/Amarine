package com.app.amarine

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.screen.add_note.addNoteScreenRoute
import com.app.amarine.ui.screen.change_password.changePasswordScreenRoute
import com.app.amarine.ui.screen.detail_article.detailArticleScreenRoute
import com.app.amarine.ui.screen.detail_member.detailMemberScreenRoute
import com.app.amarine.ui.screen.detail_note.detailNoteScreenRoute
import com.app.amarine.ui.screen.detail_stock.detailStockScreenRoute
import com.app.amarine.ui.screen.edit_note.editNoteScreenRoute
import com.app.amarine.ui.screen.edit_profile.editProfileScreen
import com.app.amarine.ui.screen.favorite_article.favoriteArticles
import com.app.amarine.ui.screen.forgot_password.forgotPasswordScreenRoute
import com.app.amarine.ui.screen.home.homeScreenRoute
import com.app.amarine.ui.screen.login.loginScreenRoute
import com.app.amarine.ui.screen.member.memberScreenRoute
import com.app.amarine.ui.screen.new_password.newPasswordScreenRoute
import com.app.amarine.ui.screen.note.noteScreenRoute
import com.app.amarine.ui.screen.onboarding.onBoardingScreenRoute
import com.app.amarine.ui.screen.otp_email.otpScreenRoute
import com.app.amarine.ui.screen.profile.profileScreenRoute
import com.app.amarine.ui.screen.register.registerScreenRoute
import com.app.amarine.ui.screen.report_problem.reportProblemScreenRoute
import com.app.amarine.ui.screen.reset_password.resetPasswordScreenRoute
import com.app.amarine.ui.screen.setting_about_app.settingAboutAppScreenRoute
import com.app.amarine.ui.screen.setting_notification.settingNotificationRoute
import com.app.amarine.ui.screen.setting_profile.settingProfileRoute
import com.app.amarine.ui.screen.splash.splashScreenRoute
import com.app.amarine.ui.screen.stock.stockScreenRoute

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier,
    ) {
        splashScreenRoute(navController)
        onBoardingScreenRoute(navController)
        loginScreenRoute(navController)
        registerScreenRoute(navController)
        forgotPasswordScreenRoute(navController)
        otpScreenRoute(navController)
        resetPasswordScreenRoute(navController)
        newPasswordScreenRoute(navController )
        homeScreenRoute(navController)
        detailArticleScreenRoute(navController)
        profileScreenRoute(navController)
        editProfileScreen(navController)
        changePasswordScreenRoute(navController)
        favoriteArticles(navController)
        settingProfileRoute(navController)
        settingNotificationRoute(navController)
        reportProblemScreenRoute(navController)
        settingAboutAppScreenRoute(navController)
        noteScreenRoute(navController)
        addNoteScreenRoute(navController)
        detailNoteScreenRoute(navController)
        editNoteScreenRoute(navController)
        stockScreenRoute(navController)
        detailStockScreenRoute(navController)
        memberScreenRoute(navController)
        detailMemberScreenRoute(navController)
    }
}