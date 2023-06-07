package com.capstone.jeconn.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.utils.getTimeAgo


@Composable
fun HorizontalNotificationCard(
    title : String,
    timestamp: Long,
    onClick: () -> Unit,

    ) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable {onClick()},
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)

        ) {
            Row {
                Column(
                    modifier = Modifier
                        .width(250.dp)
                )
                {

                    Text(
                        text = title,
                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Left
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = getTimeAgo(context, timestamp * 1000),
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

        }
    }
}
