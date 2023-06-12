package com.capstone.jeconn.ui.screen.dashboard.home_screen.message_screen.detail_message_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.Message
import com.capstone.jeconn.data.entities.MessageRoomEntity
import com.capstone.jeconn.repository.ChatRepository
import com.capstone.jeconn.state.UiState
import java.io.File

class DetailMessageViewModel(
    private val roomChatId: String,
    private val chatRepository: ChatRepository
) : ViewModel() {
    val loadMessageState: State<UiState<MessageRoomEntity>> = chatRepository.loadMessageState
    val sendImageMessageState: State<UiState<String>> = chatRepository.sendImageMessageState

    fun sendMessage(message: Message) {
        chatRepository.sendMessage(
            roomChatId = roomChatId,
            message = message
        )
    }

    fun sendImageMessage(file: File, roomChatId: String, username: String) {
        chatRepository.sendImageMessage(file = file, roomChatId = roomChatId, username = username)
    }

    init {
        chatRepository.loadChat(roomChatId)
    }
}