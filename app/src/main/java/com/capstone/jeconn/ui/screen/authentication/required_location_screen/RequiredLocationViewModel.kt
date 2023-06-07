package com.capstone.jeconn.ui.screen.authentication.required_location_screen

import androidx.lifecycle.ViewModel
import com.capstone.jeconn.repository.AuthRepository
import com.capstone.jeconn.state.UiState
import kotlinx.coroutines.flow.StateFlow

class RequiredLocationViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val pushLocationState: StateFlow<UiState<String>> = authRepository.updateLocationState

    fun getLocation() {
        authRepository.getLocation()
    }
}