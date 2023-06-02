package com.capstone.jeconn.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.capstone.jeconn.ui.screen.authentication.login_screen.LoginScreen
import com.capstone.jeconn.ui.screen.authentication.register_screen.RegisterScreen
import com.capstone.jeconn.ui.screen.authentication.required_info_screen.RequiredInfoScreen
import com.capstone.jeconn.ui.screen.dashboard.BaseScreen
import com.capstone.jeconn.ui.screen.dashboard.profile_screen.setting.SettingScreen
import com.capstone.jeconn.utils.navigateTo
import com.capstone.jeconn.utils.navigateToTop
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SetupNavGraph(
    navHostController: NavHostController
) {
    val auth = Firebase.auth
    val activity = LocalContext.current as Activity
    NavHost(
        navController = navHostController,
        route = NavRoute.ROOT.route,
        startDestination = NavRoute.LoginScreen.route
    ) {
        composable(
            route = NavRoute.LoginScreen.route
        ) {
            if (auth.currentUser != null) {
                navigateTo(navHostController, NavRoute.RequiredInfoScreen)
            } else {
                LoginScreen(navHostController = navHostController)
            }
        }
        composable(
            route = NavRoute.RegisterScreen.route
        ) {
            RegisterScreen(navHostController = navHostController)
        }

        composable(
            route = NavRoute.RequiredInfoScreen.route
        ) {
            BackHandler {
                activity.finish()
            }
            if (auth.currentUser != null) {
                if (auth.currentUser!!.isEmailVerified) {
                    navigateToTop(navHostController, NavRoute.Dashboard)
                } else {
                    RequiredInfoScreen(navHostController = navHostController)
                }
            }
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