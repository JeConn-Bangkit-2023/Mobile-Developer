package com.capstone.jeconn.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit,
) {

    FilledTonalButton(
        onClick = { onClick() },
        enabled = enabled,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
        modifier = modifier
            .height(45.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = Font.QuickSand,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}