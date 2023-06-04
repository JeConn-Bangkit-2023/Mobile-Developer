package com.capstone.jeconn.ui.screen.authentication.required_info_screen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.capstone.jeconn.repository.AuthRepository
import com.capstone.jeconn.state.UiState

class RequireInfoViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    val sentEmailVerification: State<UiState<String>> by lazy {
        repository.sendEmailVerificationState
    }
    val isEmailVerifiedState: State<UiState<String>> by lazy {
        repository.isEmailVerifiedState
    }


    fun sendEmailVerification() {
        repository.sendEmailVerification()
    }

    fun isEmailVerified() {
        repository.isEmailVerified()
    }
}