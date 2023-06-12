package com.capstone.jeconn.ui.screen.dashboard.home_screen.message_screen


import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.CustomEmptyScreen
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.HorizontalMessageCard
import com.capstone.jeconn.data.entities.MessageRoomEntity
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.MessageViewModelFactory
import com.capstone.jeconn.utils.cutTextLength
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MessageScreen(navHostController: NavHostController) {
    val auth = Firebase.auth
    val context = LocalContext.current

    val isLoading = remember {
        mutableStateOf(false)
    }
    if (isLoading.value) {
        CustomDialogBoxLoading()
    }

    val messageList = remember {
        mutableStateListOf<MessageRoomEntity>()
    }

    val messageViewModel: MessageViewModel = remember {
        MessageViewModelFactory(
            Injection.provideChatRepository(
                context = context,
            ),
            roomChatId = "",
        ).create(
            MessageViewModel::class.java
        )
    }

    val loadMessageListState by rememberUpdatedState(newValue = messageViewModel.loadMessageListState.value)


    LaunchedEffect(loadMessageListState) {
        when (val currentState = loadMessageListState) {
            is UiState.Loading -> {
                isLoading.value = true
            }

            is UiState.Success -> {
                isLoading.value = false
                messageList.addAll(currentState.data.sortedByDescending { it.messages?.values?.last()?.date })
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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(12.dp),
        ) {
            if (messageList.isNotEmpty()) {
                items(messageList) { message ->

                    val isMessage = !message.messages?.values?.last()?.message.isNullOrEmpty()
                    val isImage = !message.messages?.values?.last()?.image_url.isNullOrEmpty()
                    val isInvoice = message.messages?.values?.last()?.invoice_id != null
                    val senderUsername = message.messages?.values?.last()?.username ?: ""
                    val targetName = message.currentTargetName ?: ""

                    HorizontalMessageCard(
                        profileImageUrl = message.currentTargetImageUrl ?: "",
                        name = message.currentTargetName ?: "",
                        message = if (isMessage) {
                            if (auth.currentUser!!.displayName!! == senderUsername) {
                                "${context.getString(R.string.You)}: ${
                                    cutTextLength(
                                        message.messages?.values?.last()?.message ?: "",
                                        22
                                    )
                                }"
                            } else {
                                "$targetName: ${
                                    cutTextLength(
                                        message.messages?.values?.last()?.message ?: "",
                                        22
                                    )
                                }"
                            }
                        } else if (isImage) {
                            if (auth.currentUser!!.displayName!! == senderUsername) {
                                "${context.getString(R.string.You)}: ${context.getString(R.string.send_picture)}"
                            } else {
                                "$targetName: ${context.getString(R.string.send_picture)}"
                            }
                        } else if (isInvoice) {
                            if (auth.currentUser!!.displayName!! == senderUsername) {
                                "${context.getString(R.string.You)}: ${context.getString(R.string.made_an_invoice)}"
                            } else {
                                "$targetName: ${context.getString(R.string.made_an_invoice)}"
                            }
                        } else {
                            ""
                        },
                        timestamp = message.messages?.values?.last()?.date ?: 0,
                    ) {
                        navHostController.navigate(
                            NavRoute.DetailMessageScreen.navigateWithId(
                                getId = message.messages_room_id.toString(),
                                getName = targetName,
                                getProfileImage = Uri.encode(message.currentTargetImageUrl)
                                    .toString()
                            )
                        )
                    }
                }
            } else {
                item {

                    Spacer(modifier = Modifier.padding(vertical = 12.dp))

                    CustomEmptyScreen(
                        subject = context.getString(R.string.empty_message_list),
                        buttonLabel = context.getString(R.string.refresh),
                        buttonIcon = Icons.Default.Refresh,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        messageViewModel.loadMessage()
                    }
                }
            }
        }
    }
}


