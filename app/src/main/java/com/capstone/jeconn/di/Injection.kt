package com.capstone.jeconn.di

import android.content.Context
import com.capstone.jeconn.repository.AuthRepository
import com.capstone.jeconn.repository.ProfileRepository

object Injection {
    fun provideAuthRepository(context: Context) = AuthRepository(context)

    fun provideProfileRepository(context: Context) = ProfileRepository(context)
}