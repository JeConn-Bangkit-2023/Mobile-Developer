package com.capstone.jeconn.ui.screen.dashboard.profile_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.repository.ProfileRepository
import com.capstone.jeconn.state.UiState

class ProfileViewModel(
    private val profileRepository: ProfileRepository
): ViewModel() {
    val getPublicDataState: State<UiState<PublicDataEntity>> = profileRepository.getPublicDataState

    fun getPublicData() {
        profileRepository.getPublicData()
    }

    init {
        getPublicData()
    }
}