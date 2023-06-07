package com.capstone.jeconn.ui.screen.dashboard.home_screen.notification_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.HorizontalNotificationCard
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.navigation.NavRoute
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun NotificationScreen(navHostController: NavHostController){
    val auth = Firebase.auth
    val context = LocalContext.current
    val notificationData = DummyData.notificationData.values.toList()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        item {
            CustomNavbar {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    IconButton(

                        onClick = { navHostController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                    Text(
                        text = context.getString(R.string.notification),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
        }
        items(notificationData){notificationData->
            HorizontalNotificationCard(
                title = notificationData.title!!,
                timestamp = notificationData.timestamp!!,

                ){
                navHostController.navigate(NavRoute.DetailNotificationScreen.navigateWithId(notificationData.id.toString()))
            }
        }
        item {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}