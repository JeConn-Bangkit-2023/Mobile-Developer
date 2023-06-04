package com.capstone.jeconn.di

import android.content.Context
import com.capstone.jeconn.repository.AuthRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        return AuthRepository(context)
    }
}