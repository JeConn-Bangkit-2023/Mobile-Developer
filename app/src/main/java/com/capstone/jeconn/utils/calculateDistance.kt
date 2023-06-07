package com.capstone.jeconn.utils

import com.capstone.jeconn.data.entities.LocationEntity
import kotlin.math.pow

fun calculateDistance(point1: LocationEntity, point2: LocationEntity): String {
    val earthRadius = 6371 // radius of the earth in kilometers


    val lat1 = Math.toRadians(point1.latitude!!)
    val lon1 = Math.toRadians(point1.longitude!!)
    val lat2 = Math.toRadians(point2.latitude!!)
    val lon2 = Math.toRadians(point2.longitude!!)

    val dLat = lat2 - lat1
    val dLon = lon2 - lon1

    val a = kotlin.math.sin(dLat / 2)
        .pow(2) + kotlin.math.cos(lat1) * kotlin.math.cos(lat2) * kotlin.math.sin(
        dLon / 2
    ).pow(2)
    val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))

    val distance = earthRadius * c

    val formattedDistance = String.format("%.1f", distance)

    return "$formattedDistance Kilometer"
}