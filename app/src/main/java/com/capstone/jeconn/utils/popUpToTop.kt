package com.capstone.jeconn.utils

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.capstone.jeconn.navigation.NavRoute

fun navigateToTop(
    navHostController: NavHostController,
    destination: NavRoute,
) {
    navHostController.navigate(destination.route) {
        launchSingleTop = true
        popUpTo(destination.route) {
            inclusive = true
        }
    }
}