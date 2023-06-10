package com.capstone.jeconn.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.capstone.jeconn.ui.screen.authentication.login_screen.LoginScreen
import com.capstone.jeconn.ui.screen.authentication.register_screen.RegisterScreen
import com.capstone.jeconn.ui.screen.authentication.required_info_screen.RequiredInfoScreen
import com.capstone.jeconn.ui.screen.authentication.required_location_screen.RequiredLocationScreen
import com.capstone.jeconn.ui.screen.dashboard.BaseScreen
import com.capstone.jeconn.ui.screen.dashboard.home_screen.CreateInvoiceScreen
import com.capstone.jeconn.ui.screen.dashboard.home_screen.DetailMessageScreen
import com.capstone.jeconn.ui.screen.dashboard.home_screen.message.MessageScreen
import com.capstone.jeconn.ui.screen.dashboard.home_screen.notification_screen.NotificationScreen
import com.capstone.jeconn.ui.screen.dashboard.home_screen.notification_screen.detail_notification_screen.DetailNotificationScreen
import com.capstone.jeconn.ui.screen.dashboard.profile_screen.edit_detail_info.EditDetailInfoScreen
import com.capstone.jeconn.ui.screen.dashboard.profile_screen.myprofile.MyProfileScreen
import com.capstone.jeconn.ui.screen.dashboard.profile_screen.setting.SettingScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SetupNavGraph(
    navHostController: NavHostController
) {
    val auth = Firebase.auth
    auth.currentUser?.reload()
    val activity = LocalContext.current as Activity
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
            route = NavRoute.RequiredInfoScreen.route,
            arguments = listOf(
                navArgument(A_ARGS_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            BackHandler {
                activity.finish()
            }
            val getA = it.arguments?.getString(A_ARGS_KEY)
            RequiredInfoScreen(navHostController = navHostController, getA)
        }

        composable(
            route = NavRoute.RequiredLocationScreen.route,
        ) {
            BackHandler {
                activity.finish()
            }
            RequiredLocationScreen(navHostController = navHostController)
        }

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

        composable(
            route = NavRoute.EditDetailInfoScreen.route,
        ) {
            EditDetailInfoScreen(navHostController = navHostController)
        }

        composable(
            route = NavRoute.MyProfileScreen.route,
        ) {
            MyProfileScreen(navHostController = navHostController)
        }

        composable(
            route = NavRoute.DetailMessageScreen.route,
            arguments = listOf(
                navArgument(A_ARGS_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            val getA = it.arguments?.getString(A_ARGS_KEY)
            DetailMessageScreen(navHostController = navHostController, getA)
        }

        composable(
            route = NavRoute.CreateInvoiceScreen.route,
            arguments = listOf(
                navArgument(A_ARGS_KEY) {
                    type = NavType.StringType
                },
                navArgument(B_ARGS_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            val getA = it.arguments?.getString(A_ARGS_KEY)
            val getB = it.arguments?.getString(B_ARGS_KEY)
            CreateInvoiceScreen(navHostController = navHostController, getA, getB)
        }

        composable(
            route = NavRoute.NotificationScreen.route,
        ) {
            NotificationScreen(navHostController = navHostController)
        }

        composable(
            route = NavRoute.DetailNotificationScreen.route,
            arguments = listOf(
                navArgument(A_ARGS_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            val getA = it.arguments?.getString(A_ARGS_KEY)
            DetailNotificationScreen(navHostController = navHostController, getA)
        }

        composable(
            route = NavRoute.MessageScreen.route,
        ) {
            MessageScreen(navHostController = navHostController)
        }
    }
}