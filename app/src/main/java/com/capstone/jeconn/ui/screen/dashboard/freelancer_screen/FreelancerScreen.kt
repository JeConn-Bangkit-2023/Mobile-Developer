package com.capstone.jeconn.ui.screen.dashboard.freelancer_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.component.Font

@Composable
fun FreelancerScreen(navHostController: NavHostController, myPaddingValues: PaddingValues) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Freelancer Screen",
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
        )
    }
}