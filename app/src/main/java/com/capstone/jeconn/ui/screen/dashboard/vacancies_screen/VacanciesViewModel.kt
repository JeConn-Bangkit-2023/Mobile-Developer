package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.VacanciesEntity
import com.capstone.jeconn.repository.VacanciesRepository
import com.capstone.jeconn.state.UiState

class VacanciesViewModel(
    private val repository: VacanciesRepository,
): ViewModel() {
    val vacanciesListState: State<UiState<List<VacanciesEntity>>> = repository.vacanciesListState

    private fun getVacanciesList() {
        repository.getVacanciesList()
    }

    init {
        getVacanciesList()
    }
}