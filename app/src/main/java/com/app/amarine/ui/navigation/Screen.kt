package com.app.amarine.ui.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")
    data object OnBoarding : Screen("onboarding")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object EditProfile : Screen("edit_profile")
    data object ChangePassword : Screen("change_password")
    data object Catatan : Screen("catatan")
    data object Stock : Screen("stock")
    data object Anggota : Screen("anggota")
    data object Profile : Screen("profile")
    data object DetailArticle : Screen("detail_article")
    data object ForgotPassword : Screen("forgot_password")
    data object OTPEmail : Screen("otp_email")
    data object ResetPassword : Screen("reset_password")
    data object NewPassword : Screen("new_password")
    data object FavoriteArticles : Screen("favorite_articles")
    data object SettingProfile : Screen("setting_profile")
    data object SettingNotification : Screen("setting_notification")
    data object SettingReportProblem : Screen("setting_report_problem")
    data object SettingAboutApp : Screen("setting_about_app")
    data object AddNote : Screen("add_note")
    data object DetailNote : Screen("detail_note")
    data object EditNote : Screen("edit_note")

    // Rute dengan Parameter
    data object DetailStock : Screen("detail_stock/{stockId}") {
        fun createRoute(stockId: Int) = "detail_stock/$stockId"
    }

    data object DetailAnggota : Screen("detail_anggota/{anggotaId}") {
        fun createRoute(anggotaId: Int) = "detail_anggota/$anggotaId"
    }
}
