package com.capstone.jeconn.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomEmptyScreen(
    subject: String,
    buttonLabel: String,
    buttonIcon: ImageVector,
    modifier: Modifier = Modifier,
    buttonOnClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = subject,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Normal,
            ),
        )

        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        CustomFlatIconButton(
            icon = buttonIcon,
            label = buttonLabel
        ) {
            buttonOnClick()
        }
    }
}