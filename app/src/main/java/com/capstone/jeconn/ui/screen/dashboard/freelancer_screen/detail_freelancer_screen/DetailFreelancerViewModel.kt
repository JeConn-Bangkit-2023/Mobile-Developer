package com.capstone.jeconn.ui.screen.dashboard.freelancer_screen.detail_freelancer_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.repository.ChatRepository
import com.capstone.jeconn.repository.FreelancerRepository
import com.capstone.jeconn.state.UiState

class DetailFreelancerViewModel(
    private val freelancerRepository: FreelancerRepository,
    private val chatRepository: ChatRepository,
    private val username: String,
) : ViewModel() {
    val loadFreelancerState: State<UiState<PublicDataEntity>> = freelancerRepository.loadFreelancerState
    val openChatState: State<UiState<Long>> = chatRepository.openChatChatState

    fun loadFreelancer(username: String) {
        freelancerRepository.loadFreelancer(username)
    }

    fun openChat(myUsername: String, targetUsername: String) {
        chatRepository.openChat(myUsername, targetUsername)
    }

    init {
        loadFreelancer(username)
    }
}