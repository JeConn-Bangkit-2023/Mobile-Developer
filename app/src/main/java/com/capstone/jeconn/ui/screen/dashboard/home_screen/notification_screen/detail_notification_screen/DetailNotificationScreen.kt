package com.capstone.jeconn.ui.screen.dashboard.home_screen.notification_screen.detail_notification_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.utils.getTimeAgo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun DetailNotificationScreen(navHostController: NavHostController, id: String?) {
    val auth = Firebase.auth
    val context = LocalContext.current
    //val notificationData = DummyData.notificationData.values.toList()
    val getId = id!!.toInt()
    val notificationData = DummyData.notificationData[getId]
    val title = notificationData!!.title
    val time = notificationData.timestamp!!
    val dec = notificationData.description!!


    Log.e("deskripsi", notificationData.description)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
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
                        text = context.getString(R.string.detail_notification),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row {
                    Column(
                        modifier = Modifier
                            .width(250.dp)
                    ) {
                        Text(
                            text = title!!,
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
                        text = getTimeAgo(context, time * 1000),
                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }

                Spacer(modifier = Modifier.padding(12.dp))

                Text(
                    text = dec,
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left
                    )
                )
            }

        }

        /*
        items(notificationData){notificationData->
        HorizontalNotificationCard(
            title = notificationData.title,
            timestamp = notificationData.timestamp,

            ){
            navHostController.navigate(NavRoute.DetailNotificationScreen.navigateWithId(notificationData.id.toString()))
        }
    }
    */

        item {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
        }
    }


}