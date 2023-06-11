package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen.create_vacancies_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.repository.VacanciesRepository
import com.capstone.jeconn.state.UiState

class CreateVacanciesViewModel(
    private val repository: VacanciesRepository
) : ViewModel() {
    val createVacanciesState: State<UiState<String>> = repository.createVacanciesState

    fun createVacancies(
        category: Map<String, Int>,
        description: String,
        salaryStart: Long,
        salaryEnd: Long
    ) {
        repository.createVacancies(
            category,
            description,
            salaryStart,
            salaryEnd
        )
    }
}