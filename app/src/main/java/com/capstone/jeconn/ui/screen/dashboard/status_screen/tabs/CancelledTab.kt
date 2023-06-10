package com.capstone.jeconn.ui.screen.dashboard.status_screen.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
fun CancelledTab(navHostController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Cancelled Tab",
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
        )
    }
}