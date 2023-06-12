package com.capstone.jeconn.ui.screen.dashboard.freelancer_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.repository.FreelancerRepository
import com.capstone.jeconn.state.UiState

class FreelancerViewModel(
    private val freelancerRepository: FreelancerRepository,
) : ViewModel() {
    val loadFreelancerListState: State<UiState<MutableList<PublicDataEntity>>> = freelancerRepository.loadFreelancerListState

    private fun loadFreelancerList() {
        freelancerRepository.loadFreelancerList()
    }

    init {
        loadFreelancerList()
    }
}