package com.capstone.jeconn.navigation

sealed class NavRoute (val route: String) {
    object ROOT: NavRoute(route = "root")
    object LoginScreen: NavRoute(route = "login_screen")
    object RegisterScreen: NavRoute(route = "register_screen")
    object RequiredInfoScreen: NavRoute(route = "register_screen")
    object BaseScreen: NavRoute(route = "base_screen")
    object HomeScreen: NavRoute(route = "home_screen")
    object VacanciesScreen: NavRoute(route = "vacancies_screen")
    object FreelancerScreen: NavRoute(route = "freelance_screen")
    object StatusScreen: NavRoute(route = "status_screen")
    object ProfileScreen: NavRoute(route = "profile_screen")
}