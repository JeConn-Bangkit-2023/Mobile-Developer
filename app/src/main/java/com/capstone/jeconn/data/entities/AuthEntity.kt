package com.capstone.jeconn.data.entities

data class AuthEntity(
    val email: String,
    val password: String,
    val username: String = "0",
)
