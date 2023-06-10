package com.capstone.jeconn.ui.screen.dashboard.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.capstone.jeconn.component.CustomRoundedIcon
import com.capstone.jeconn.component.CustomTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.message_item.type.CustomMessageItem
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.navigation.NavRoute

@Composable
fun DetailMessageScreen(navHostController: NavHostController, id: String?) {

    val context = LocalContext.current
    val myUsername = DummyData.privateData[DummyData.UID]!!.username!!
    val chatId = id!!.toInt()
    val messageRoom = DummyData.messageRooms[chatId]!!
    val enemyUsername = messageRoom.members_username!!.filter { it != myUsername }[0]
    val enemyName = DummyData.publicData[enemyUsername]!!.full_name!!
    val invoiceList = DummyData.invoices

    val messageState = rememberSaveable {
        mutableStateOf("")
    }
    val menuExpanded = rememberSaveable() {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CustomNavbar {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navHostController.popBackStack()
                    }
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Text(
                text = enemyName,
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(messageRoom.messages!!) { itemChat ->
                    CustomMessageItem(
                        text = itemChat.message,
                        imageUrl = itemChat.image_url,
                        invoice = invoiceList[itemChat.invoice_id?.toInt()],
                        senderProfileImageUrl = DummyData.publicData[itemChat.username]!!.profile_image_url!!,
                        isMine = itemChat.username == myUsername,
                        dateTime = itemChat.date!!
                    )
                }
            }
        }

        if (menuExpanded.value) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .padding(top = 0.dp, bottom = 12.dp, start = 8.dp, end = 8.dp),
            ) {
                CustomRoundedIcon(
                    icon = R.drawable.ic_invoice,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(50.dp)

                ) {
                    navHostController.navigate(
                        NavRoute.CreateInvoiceScreen.navigateWithData(
                            tenant = enemyUsername,
                            freelancer = myUsername
                        )
                    )
                }
                CustomRoundedIcon(
                    icon = R.drawable.ic_image,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(50.dp)

                ) {
                    //TODO
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    if (menuExpanded.value) {
                        RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
                    } else {
                        RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    }
                )
                .background(MaterialTheme.colorScheme.inversePrimary)
                .padding(top = 0.dp, bottom = 12.dp, start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CustomTextField(
                label = context.getString(R.string.write_a_message),
                state = messageState,
                length = 500,
                rounded = 50.dp,
                modifier = Modifier
                    .weight(1f)
                    .height(65.dp)

            )

            CustomRoundedIcon(
                icon = if (messageState.value != "") {
                    Icons.Default.Send
                } else {
                    Icons.Default.MoreVert
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(50.dp)

            ) {
                if (messageState.value != "") {
                    //TODO
                } else {
                    menuExpanded.value = !menuExpanded.value
                }
            }
        }
    }
}