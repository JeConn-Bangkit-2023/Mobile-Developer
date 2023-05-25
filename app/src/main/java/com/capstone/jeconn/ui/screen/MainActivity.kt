package com.capstone.jeconn.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.jeconn.navigation.SetupNavGraph
import com.capstone.jeconn.ui.theme.JeConnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JeConnTheme {
                // A surface container using the 'background' color from the theme
                Surface() {
                    val navHostController: NavHostController = rememberNavController()
                    SetupNavGraph(navHostController = navHostController)
                }
            }
        }
    }
}