package com.capstone.jeconn.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomChip(
    iconId: ImageVector,
    isSelected: Boolean,
    text: String,
    onChecked: (Boolean) -> Unit,
) {
    val shape = RoundedCornerShape(8.dp)
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 4.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = shape
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = shape
            )
            .clip(shape = shape)
            .clickable {
                onChecked(!isSelected)
            }
            .padding(vertical = 4.dp, horizontal = 12.dp)
    ) {
        if (isSelected) {
            Icon(
                imageVector = iconId,
                tint = DarkGray,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
        Text(
            text = text,
            color = Unspecified
        )
    }
}