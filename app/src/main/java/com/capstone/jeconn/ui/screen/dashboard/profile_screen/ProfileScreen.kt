package com.capstone.jeconn.ui.screen.dashboard.profile_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.HorizontalProfileCard
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.navigateTo

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        val uid = DummyData.UID
        val privateData = DummyData.privateData[uid]!!
        val publicData = DummyData.publicData[privateData.username]!!

        CustomNavbar(
            modifier = Modifier
                .height(128.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CropToSquareImage(
                    imageUrl = publicData.profile_image_url,
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )

                Column() {
                    Text(
                        text = publicData.full_name,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Text(
                        text = "@${publicData.username}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Normal,
                        )
                    )

                    Text(
                        text = privateData.email,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Normal,
                        )
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {

            HorizontalProfileCard(
                subject = context.getString(R.string.profile_unready_text),
                icon = Icons.Default.Person,
            )

            HorizontalProfileCard(
                subject = context.getString(R.string.setting),
                icon = Icons.Default.Settings,
            ) {
                navigateTo(navHostController, NavRoute.SettingScreen)
            }
        }
    }
}