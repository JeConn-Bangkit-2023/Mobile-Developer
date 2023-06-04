package com.capstone.jeconn.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDivider() {
    Divider(
        color = Color.Black,
        thickness = 0.5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    )
}