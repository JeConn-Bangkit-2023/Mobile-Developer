package com.capstone.jeconn.data.entities

// Key: Int
data class VacanciesEntity(
    val id: Long? = null,
    val username: String? = null,
    val full_name: String? = null,
    val imageUrl: String? = null,
    val timestamp: Long? = null,
    val start_salary: Long? = null,
    val end_salary: Long? = null,
    val category: Map<String, Int>? = null,
    val description: String? = null,
    val location: LocationEntity? = null,
    val myLocation: LocationEntity? = null
)