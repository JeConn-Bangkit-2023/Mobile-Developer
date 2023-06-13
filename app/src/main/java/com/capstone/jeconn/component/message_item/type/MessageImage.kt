package com.capstone.jeconn.component.message_item.type

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.getTimeAgo

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MessageImage(
    imageUrl: String,
    isMine: Boolean,
    senderProfileImageUrl: String,
    dateTime: Long
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val paddingValues = if (isMine) {
        PaddingValues(start = 128.dp)
    } else {
        PaddingValues(end = 64.dp)
    }

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = if (isMine) {
            Arrangement.End
        } else {
            Arrangement.Start
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        if (!isMine) {
            CropToSquareImage(
                imageUrl = senderProfileImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        }

        Column(
            horizontalAlignment = if (isMine) {
                Alignment.End
            } else {
                Alignment.Start
            }
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        if (isMine) {
                            RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp,
                                bottomStart = 12.dp,
                                bottomEnd = 0.dp
                            )
                        } else {
                            RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 12.dp
                            )
                        }
                    )
                    .background(
                        if (isMine) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                    .padding(8.dp)
                    .heightIn(max = 248.dp)
                    .clickable {
                        showDialog.value = true
                    }
            ) {
                GlideImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(
                            if (isMine) {
                                RoundedCornerShape(
                                    topStart = 12.dp,
                                    topEnd = 12.dp,
                                    bottomStart = 12.dp,
                                    bottomEnd = 0.dp
                                )
                            } else {
                                RoundedCornerShape(
                                    topStart = 12.dp,
                                    topEnd = 12.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 12.dp
                                )
                            }
                        )
                        .align(Alignment.Center)
                ) {
                    it
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = getTimeAgo(context, dateTime),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
        ) {
            Box(
                modifier = Modifier
                    .clickable { showDialog.value = false },
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                ) {
                    it
                }
            }
        }
    }
}
