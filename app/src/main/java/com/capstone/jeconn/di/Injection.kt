package com.capstone.jeconn.di

import android.content.Context
import com.capstone.jeconn.ui.screen.authentication.AuthRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository{
        return AuthRepository(context)
    }
}