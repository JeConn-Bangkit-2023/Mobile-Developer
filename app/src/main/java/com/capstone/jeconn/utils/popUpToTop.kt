package com.capstone.jeconn.utils

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.popUpToTop(navController: NavHostController) {
    launchSingleTop = true
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive =  true
    }
}