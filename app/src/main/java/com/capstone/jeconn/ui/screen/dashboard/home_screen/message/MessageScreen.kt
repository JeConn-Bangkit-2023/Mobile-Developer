package com.capstone.jeconn.ui.screen.dashboard.home_screen.message


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.HorizontalMessageCard
import com.capstone.jeconn.data.dummy.DummyData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MessageScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val massageData = DummyData.messageRooms
    val uid = DummyData.UID
    val myData = DummyData.privateData[uid]
    val myRoomChatId = DummyData.publicData[myData!!.username]!!.messages_room_id
    val publicData = DummyData.publicData

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
                        text = context.getString(R.string.Message),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
        }

        items(
            myRoomChatId!!
        ) { id ->
            val enemyUsername =
                massageData[id]!!.members_username!!.filter { it != myData.username }
                    .joinToString { it }
            HorizontalMessageCard(
                profileImageUrl = publicData[enemyUsername]!!.profile_image_url!!,
                name = publicData[enemyUsername]!!.full_name!!,
                message = massageData[id]!!.messages!!.last().message!!,
                timestamp = massageData[id]!!.messages!!.last().date!!,
            ) {
                //TODO
            }
        }

        item {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}


