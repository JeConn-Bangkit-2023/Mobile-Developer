package com.capstone.jeconn.ui.screen.dashboard.home_screen.message_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.MessageRoomEntity
import com.capstone.jeconn.repository.ChatRepository
import com.capstone.jeconn.state.UiState

class MessageViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {
    val loadMessageListState: State<UiState<List<MessageRoomEntity>>> =
        chatRepository.loadMessageListState

    fun loadMessage() {
        chatRepository.loadMessageList()
    }

    init {
        chatRepository.loadMessageList()
    }
}