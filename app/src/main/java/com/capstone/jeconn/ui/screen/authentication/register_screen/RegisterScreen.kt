package com.capstone.jeconn.ui.screen.authentication.register_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.utils.popUpToTop

@Composable
fun RegisterScreen(navHostController: NavHostController) {
    Log.e("RegisterScreen", "test")
    val context = LocalContext.current
    //Login Button
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomButton(
            text = context.getString(R.string.login),
            modifier = Modifier
                .padding(vertical = 24.dp)
        ) {
            navHostController.navigate(NavRoute.Dashboard.route) {
                popUpToTop(navHostController)
            }
        }
    }
}