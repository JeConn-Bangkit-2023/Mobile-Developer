package com.capstone.jeconn.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.jeconn.ui.screen.authentication.login_screen.LoginScreen
import com.capstone.jeconn.ui.screen.authentication.register_screen.RegisterScreen
import com.capstone.jeconn.ui.screen.authentication.required_info_screen.RequiredInfoScreen
import com.capstone.jeconn.ui.screen.dashboard.BaseScreen
import com.capstone.jeconn.ui.screen.dashboard.profile_screen.setting.SettingScreen

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
            startDestination = NavRoute.BaseScreen.route,
            route = NavRoute.Dashboard.route
        ) {
            composable(
                route = NavRoute.BaseScreen.route,
            ) {
                BaseScreen(navHostController = navHostController)
            }
            composable(
                route = NavRoute.SettingScreen.route,
            ) {
                SettingScreen(navHostController = navHostController)
            }
        }
    }
}