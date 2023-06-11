package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen.detail_vacancies_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.VacanciesEntity
import com.capstone.jeconn.repository.VacanciesRepository
import com.capstone.jeconn.state.UiState

class DetailVacanciesViewModel(
    private val repository: VacanciesRepository,
    private val vacanciesId: Int
) : ViewModel() {
    val vacanciesDetailState: State<UiState<VacanciesEntity>> = repository.vacanciesDetailState

    fun getVacanciesDetail(id: Int) {
        repository.getVacanciesDetail(id)
    }

    init {
        getVacanciesDetail(vacanciesId)
    }
}