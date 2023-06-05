package com.capstone.jeconn.utils

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("SimpleDateFormat")
fun dateToTimeStamp(pattern: String, date: String, withHours: Boolean): Long {

    val newPattern = if (withHours) DateTimeFormatter.ofPattern(pattern) else DateTimeFormatter.ofPattern("$pattern HH:mm")
    val localDate = if (withHours) LocalDateTime.parse(date, newPattern) else LocalDateTime.parse("$date 00:00", newPattern)
    return localDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}