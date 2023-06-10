package com.capstone.jeconn.ui.screen.dashboard.profile_screen.myprofile

import androidx.lifecycle.ViewModel
import com.capstone.jeconn.repository.ProfileRepository
import com.capstone.jeconn.state.UiState
import java.io.File
import androidx.compose.runtime.State

class MyProfileViewModel(
    private val profileRepository: ProfileRepository,
    ) : ViewModel() {
    val updateProfileImageState: State<UiState<String>> = profileRepository.updateProfileImageState

    fun updateProfile(file: File) {
        profileRepository.updateProfileImage(file)
    }
}