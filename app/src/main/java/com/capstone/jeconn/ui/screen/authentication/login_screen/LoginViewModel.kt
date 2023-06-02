package com.capstone.jeconn.ui.screen.authentication.login_screen

import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.AuthEntity
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.ui.screen.authentication.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val registerState: StateFlow<UiState<String>> by lazy {
        authRepository.loginState.asStateFlow()
    }

    fun loginUser(user: AuthEntity) {
        authRepository.loginUser(user)
    }
}