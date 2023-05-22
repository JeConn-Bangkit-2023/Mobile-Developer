package com.capstone.jeconn.navigation

sealed class NavRoute (val route: String) {
    object ROOT : NavRoute(route = "root")
    object LoginScreen : NavRoute(route = "login_screen")
    object RegisterScreen : NavRoute(route = "register_screen")
    object RequiredInfoScreen : NavRoute(route = "register_screen")
}