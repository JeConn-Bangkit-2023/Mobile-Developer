package com.capstone.jeconn.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.jeconn.navigation.SetupNavGraph
import com.capstone.jeconn.ui.theme.JeConnTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JeConnTheme {
                val navHostController: NavHostController = rememberNavController()
                SetupNavGraph(navHostController = navHostController)
            }
        }
    }
}