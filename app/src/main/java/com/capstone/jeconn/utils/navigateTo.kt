package com.capstone.jeconn.utils

import androidx.navigation.NavHostController
import com.capstone.jeconn.navigation.NavRoute

fun navigateTo(navHostController: NavHostController, destination: NavRoute) {
    navHostController.navigate(destination.route) {
        launchSingleTop = true
    }
}