package com.capstone.jeconn.ui.screen.dashboard.home_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.data.entities.VacanciesEntity
import com.capstone.jeconn.repository.FreelancerRepository
import com.capstone.jeconn.repository.VacanciesRepository
import com.capstone.jeconn.state.UiState

class HomeViewModel(
    private val freelancerRepository: FreelancerRepository,
    private val vacanciesRepository: VacanciesRepository,
) : ViewModel() {
    val loadFreelancerList: State<UiState<List<PublicDataEntity>>> =
        freelancerRepository.loadFreelancerListState
    val vacanciesListState: State<UiState<List<VacanciesEntity>>> =
        vacanciesRepository.vacanciesListState

    private fun loadData() {
        try {
            freelancerRepository.loadFreelancerList()
        } finally {
            vacanciesRepository.getVacanciesList()
        }
    }

    init {
        loadData()
    }
}