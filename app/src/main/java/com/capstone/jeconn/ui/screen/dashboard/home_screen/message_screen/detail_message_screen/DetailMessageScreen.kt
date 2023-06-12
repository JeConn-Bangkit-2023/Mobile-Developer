package com.capstone.jeconn.ui.screen.dashboard.home_screen.message_screen.detail_message_screen

import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.CustomRoundedIcon
import com.capstone.jeconn.component.CustomTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.message_item.type.CustomMessageItem
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.data.entities.Message
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.MessageViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun DetailMessageScreen(
    navHostController: NavHostController,
    id: String?,
    targetName: String?,
    targetImageUrl: String?
) {

    val context = LocalContext.current
    val auth = Firebase.auth.currentUser!!
    val messageRoom = remember {
        mutableStateMapOf<String, Message>()
    }

    val targetUsernameState = remember {
        mutableStateOf("")
    }

    val invoiceList = DummyData.invoices

    val isLoading = remember {
        mutableStateOf(false)
    }
    if (isLoading.value) {
        CustomDialogBoxLoading()
    }

    val messageState = rememberSaveable {
        mutableStateOf("")
    }
    val menuExpanded = rememberSaveable() {
        mutableStateOf(false)
    }

    val detailMessageViewModel: DetailMessageViewModel = remember {
        MessageViewModelFactory(
            Injection.provideChatRepository(
                context = context,
            ),
            roomChatId = id ?: "",
        ).create(
            DetailMessageViewModel::class.java
        )
    }

    val loadMessageState by rememberUpdatedState(newValue = detailMessageViewModel.loadMessageState.value)

    LaunchedEffect(loadMessageState) {
        when (val currentState = loadMessageState) {
            is UiState.Loading -> {
                isLoading.value = true
            }

            is UiState.Success -> {
                isLoading.value = false
                currentState.data.messages?.map { eachMessage ->
                    messageRoom[eachMessage.key] = eachMessage.value
                }
                currentState.data.members_username!!.keys.find { it != auth.displayName }?.let {
                    targetUsernameState.value = it
                }
            }

            is UiState.Error -> {
                isLoading.value = false
            }

            else -> {
                //Nothing
                isLoading.value = false
            }
        }
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
                text = targetName!!,
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
                items(messageRoom.toList().sortedBy { it.second.date }) { itemChat ->
                    CustomMessageItem(
                        text = itemChat.second.message,
                        imageUrl = itemChat.second.image_url,
                        invoice = invoiceList[itemChat.second.invoice_id?.toInt()],
                        senderProfileImageUrl = Uri.decode(targetImageUrl!!),
                        isMine = itemChat.second.username == auth.displayName,
                        dateTime = itemChat.second.date!!
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
//                    navHostController.navigate(
//                        NavRoute.CreateInvoiceScreen.navigateWithData(
//                            tenant = targetUsernameState.value,
//                            freelancer = auth.displayName!!
//                        )
//                    )
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
                    detailMessageViewModel.sendMessage(
                        Message(
                            date = System.currentTimeMillis(),
                            username = auth.displayName,
                            message = messageState.value
                        )
                    )
                    messageState.value = ""
                } else {
                    menuExpanded.value = !menuExpanded.value
                }
            }
        }
    }
}