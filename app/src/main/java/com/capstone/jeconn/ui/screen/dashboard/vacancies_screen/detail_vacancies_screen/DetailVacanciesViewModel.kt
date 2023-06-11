package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen.detail_vacancies_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.VacanciesEntity
import com.capstone.jeconn.repository.ChatRepository
import com.capstone.jeconn.repository.VacanciesRepository
import com.capstone.jeconn.state.UiState

class DetailVacanciesViewModel(
    private val vacanciesRepository: VacanciesRepository,
    private val chatRepository: ChatRepository,
    vacanciesId: Int
) : ViewModel() {
    val vacanciesDetailState: State<UiState<VacanciesEntity>> =
        vacanciesRepository.vacanciesDetailState
    val openChatState: State<UiState<Long>> = chatRepository.openChatChatState

    fun getVacanciesDetail(id: Int) {
        vacanciesRepository.getVacanciesDetail(id)
    }

    fun openChat(myUsername: String, targetUsername: String) {
        chatRepository.openChat(myUsername, targetUsername)
    }

    init {
        getVacanciesDetail(vacanciesId)
    }
}