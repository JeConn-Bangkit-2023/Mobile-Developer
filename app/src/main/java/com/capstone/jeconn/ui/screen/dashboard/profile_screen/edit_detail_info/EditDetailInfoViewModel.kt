package com.capstone.jeconn.ui.screen.dashboard.profile_screen.edit_detail_info

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.repository.ProfileRepository
import com.capstone.jeconn.state.UiState
import java.io.File

class EditDetailInfoViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    val getPublicDataState: State<UiState<PublicDataEntity>> = profileRepository.getPublicDataState
    val updateJobImageState: State<UiState<String>> = profileRepository.updateJobImageState
    val updateDetailInfoState: State<UiState<String>> = profileRepository.updateDetailInfoState

    fun updateDetailInfo(
        dob: Long,
        gender: String,
        aboutMe: String,
        listCategory: List<Int>,
        offers: Boolean
    ) {
        profileRepository.updateDetailInfo(dob, gender, aboutMe, listCategory, offers)
    }

    fun getPublicData() {
        profileRepository.getPublicData()
    }

    fun updateJobImage(file: File) {
        profileRepository.updateJobImage(file)
    }

    init {
        getPublicData()
    }
}