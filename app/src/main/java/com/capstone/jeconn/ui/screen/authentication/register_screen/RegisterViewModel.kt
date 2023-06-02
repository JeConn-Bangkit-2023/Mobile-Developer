package com.capstone.jeconn.ui.screen.authentication.register_screen

import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.AuthEntity
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.ui.screen.authentication.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val registerState: StateFlow<UiState<String>> by lazy {
        authRepository.registerState.asStateFlow()
    }

    fun registerUser(user: AuthEntity) {
        authRepository.registerUser(user)
    }
}