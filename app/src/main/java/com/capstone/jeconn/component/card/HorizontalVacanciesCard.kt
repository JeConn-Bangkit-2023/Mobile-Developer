package com.capstone.jeconn.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.calculateDistance
import com.capstone.jeconn.utils.cutTextLength
import com.capstone.jeconn.utils.getTimeAgo

@Composable
fun HorizontalVacanciesCard(
    profileImageUrl: String,
    name: String,
    myLocation: LocationEntity,
    targetLocation: LocationEntity,
    timestamp: Long,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable {onClick()}
                .background(MaterialTheme.colorScheme.inversePrimary)
                .padding(12.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CropToSquareImage(
                    imageUrl = profileImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = name,
                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = calculateDistance(myLocation, targetLocation),
                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = getTimeAgo(context, timestamp),
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = cutTextLength(description, 30),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}