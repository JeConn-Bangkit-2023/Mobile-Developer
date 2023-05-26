package com.capstone.jeconn.data.entities

data class VacanciesEntity(
    val username: String,
    val timestamp: Long,
    val city: String,
    val salary: Long,
    val category: List<String>,
    val description: String,
)