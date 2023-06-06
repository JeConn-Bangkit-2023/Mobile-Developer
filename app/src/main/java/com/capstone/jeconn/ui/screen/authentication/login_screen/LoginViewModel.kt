package com.capstone.jeconn.ui.screen.authentication.login_screen

import androidx.lifecycle.ViewModel
import com.capstone.jeconn.data.entities.AuthEntity
import com.capstone.jeconn.repository.AuthRepository
import com.capstone.jeconn.state.UiState
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val loginState: StateFlow<UiState<String>> by lazy {
        authRepository.loginState
    }

    fun loginUser(user: AuthEntity) {
        authRepository.loginUser(user)
    }
}