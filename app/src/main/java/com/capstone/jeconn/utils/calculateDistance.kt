package com.capstone.jeconn.utils

import com.capstone.jeconn.data.entities.LocationEntity
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

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

fun calculateDistanceToDecimal(point1: LocationEntity, point2: LocationEntity): Int {
    val lat1 = Math.toRadians(point1.latitude ?: 0.0)
    val lon1 = Math.toRadians(point1.longitude ?: 0.0)
    val lat2 = Math.toRadians(point2.latitude ?: 0.0)
    val lon2 = Math.toRadians(point2.longitude ?: 0.0)

    val dLon = lon2 - lon1
    val dLat = lat2 - lat1

    val a = sin(dLat/2).pow(2) + cos(lat1) * cos(lat2) * sin(dLon/2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1-a))
    val earthRadius = 6371000

    return (earthRadius * c).toInt()
}