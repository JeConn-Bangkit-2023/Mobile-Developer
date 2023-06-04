package com.capstone.jeconn.utils

import androidx.lifecycle.ViewModelProvider
import com.capstone.jeconn.repository.AuthRepository
import com.capstone.jeconn.ui.screen.authentication.login_screen.LoginViewModel
import com.capstone.jeconn.ui.screen.authentication.register_screen.RegisterViewModel
import com.capstone.jeconn.ui.screen.authentication.required_info_screen.RequireInfoViewModel

class AuthViewModelFactory(private val repository: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RequireInfoViewModel::class.java)) {
            return RequireInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}