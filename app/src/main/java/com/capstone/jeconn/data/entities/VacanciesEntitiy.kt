package com.capstone.jeconn.data.entities

import com.google.android.gms.maps.model.LatLng

// Key: Int
data class VacanciesEntity(
    val username: String,
    val timestamp: Long,
    val salary: Long,
    val category: List<Int>,
    val description: String,
    val location: LatLng
)