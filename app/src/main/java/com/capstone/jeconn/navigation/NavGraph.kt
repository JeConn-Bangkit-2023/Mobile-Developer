package com.capstone.jeconn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.capstone.jeconn.ui.screen.authentication.login_screen.LoginScreen
import com.capstone.jeconn.ui.screen.authentication.register_screen.RegisterScreen

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
            RegisterScreen(navHostController = navHostController)
        }
    }
}