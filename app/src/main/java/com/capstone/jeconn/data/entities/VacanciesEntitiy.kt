package com.capstone.jeconn.data.entities

// Key: Int
data class VacanciesEntity(
    val username: String? = null,
    val timestamp: Long? = null,
    val salary: Long? = null,
    val category: List<Int>? = null,
    val description: String? = null,
    val location: LocationEntity? = null
)