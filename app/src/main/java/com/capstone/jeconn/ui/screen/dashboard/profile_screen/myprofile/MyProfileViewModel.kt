package com.capstone.jeconn.ui.screen.dashboard.profile_screen.myprofile

import androidx.lifecycle.ViewModel
import com.capstone.jeconn.repository.ProfileRepository
import com.capstone.jeconn.state.UiState
import java.io.File
import androidx.compose.runtime.State
import com.capstone.jeconn.data.entities.PublicDataEntity

class MyProfileViewModel(
    private val profileRepository: ProfileRepository,
    ) : ViewModel() {
    val updateProfileImageState: State<UiState<String>> = profileRepository.updateProfileImageState
    val getPublicDataState: State<UiState<PublicDataEntity>> = profileRepository.getPublicDataState

    fun updateProfile(file: File) {
        profileRepository.updateProfileImage(file)
    }

    private fun getPublicData() {
        profileRepository.getPublicData()
    }

    init {
        getPublicData()
    }
}