package com.capstone.jeconn.navigation

const val A_ARGS_KEY = "a"
const val B_ARGS_KEY = "b"
const val C_ARGS_KEY = "c"
const val D_ARGS_KEY = "d"
const val E_ARGS_KEY = "e"

sealed class NavRoute(val route: String) {
    object ROOT : NavRoute(route = "root")
    object LoginScreen : NavRoute(route = "login_screen")
    object RegisterScreen : NavRoute(route = "register_screen")
    object RequiredInfoScreen : NavRoute(route = "required_screen/{$A_ARGS_KEY}") {
        fun navigateFromRegister(
            isFromRegister: String
        ): String {
            return "required_screen/$isFromRegister"
        }
    }

    object RequiredLocationScreen: NavRoute(route = "required_location_screen")

    object BaseScreen : NavRoute(route = "base_screen")
    object SettingScreen : NavRoute(route = "setting_screen")
    object EditDetailInfoScreen : NavRoute("edit_detail_info_screen")
    object NotificationScreen : NavRoute(route = "notification_screen")
    object DetailNotificationScreen : NavRoute(route = "detail_notification_screen/{$A_ARGS_KEY}") {
        fun navigateWithId(
            getId : String
        ): String {
            return "detail_notification_screen/$getId"
        }
    }


}