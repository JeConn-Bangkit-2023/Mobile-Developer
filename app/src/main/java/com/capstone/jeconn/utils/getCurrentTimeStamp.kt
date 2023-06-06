package com.capstone.jeconn.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun getLocalCurrentEpoch(pattern: String): Long {
    //pattern Example dd/MM/yyyy HH:mm:ss
    val sdf = SimpleDateFormat(pattern)
    val currentDate = sdf.format(Date())
    val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    val localDate = LocalDateTime.parse(currentDate, dateTimeFormatter)

    return localDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}