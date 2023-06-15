package com.capstone.jeconn.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CustomLengthCounter(
    currentLength: Int,
    maxLength: Int,
) {
    Text(
        text = "$currentLength / $maxLength",
        style = TextStyle(
            fontFamily = Font.QuickSand,
            fontWeight = if (currentLength < maxLength) {
                FontWeight.Light
            } else {
                FontWeight.Bold
            },
            fontSize = 14.sp,
            color = if (currentLength < maxLength) {
                MaterialTheme.colorScheme.onBackground
            } else {
                Color.Red
            }
        ),
    )
}