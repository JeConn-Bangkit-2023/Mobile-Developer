package com.capstone.jeconn.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar

@SuppressLint("SimpleDateFormat")
fun timeStampToDate(pattern: String, epochDate: Long?): String {
    val sdf = SimpleDateFormat(pattern)
    val calendar = Calendar.getInstance()
    if (epochDate != null) {
        calendar.timeInMillis = epochDate
    }
    return sdf.format(calendar.time)
}