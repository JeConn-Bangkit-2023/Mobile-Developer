package com.capstone.jeconn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.jeconn.ui.screen.authentication.login_screen.LoginScreen
import com.capstone.jeconn.ui.screen.authentication.register_screen.RegisterScreen
import com.capstone.jeconn.ui.screen.authentication.required_info_screen.RequiredInfoScreen
import com.capstone.jeconn.ui.screen.dashboard.freelancer_screen.FreelancerScreen
import com.capstone.jeconn.ui.screen.dashboard.home_screen.HomeScreen
import com.capstone.jeconn.ui.screen.dashboard.profile_screen.ProfileScreen
import com.capstone.jeconn.ui.screen.dashboard.status_screen.StatusScreen
import com.capstone.jeconn.ui.screen.dashboard.vacancies_screen.VacanciesScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        route = NavRoute.ROOT.route,
        startDestination = NavRoute.LoginScreen.route
    ) {

        composable(
            route = NavRoute.LoginScreen.route
        ) {
            LoginScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoute.RegisterScreen.route
        ) {
            RegisterScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoute.RequiredInfoScreen.route
        ) {
            RequiredInfoScreen(navHostController = navHostController)
        }

        // Nested Home Navigation
        navigation(
            startDestination = NavRoute.HomeScreen.route,
            route = NavRoute.BaseScreen.route
        ) {
            composable(
                route = NavRoute.HomeScreen.route
            ) {
                HomeScreen(navHostController = navHostController)
            }
            composable(
                route = NavRoute.VacanciesScreen.route
            ) {
                VacanciesScreen(navHostController = navHostController)
            }
            composable(
                route = NavRoute.FreelancerScreen.route
            ) {
                FreelancerScreen(navHostController = navHostController)
            }
            composable(
                route = NavRoute.StatusScreen.route
            ) {
                StatusScreen(navHostController = navHostController)
            }
            composable(
                route = NavRoute.ProfileScreen.route
            ) {
                ProfileScreen(navHostController = navHostController)
            }
        }
    }
}