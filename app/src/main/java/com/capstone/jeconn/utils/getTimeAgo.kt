package com.capstone.jeconn.utils

import android.content.Context
import com.capstone.jeconn.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getTimeAgo(context: Context, mills: Long): String {

    val minuteMills = 60 * 1000L
    val hourMills = 60 * minuteMills
    val dayMills = 24 * hourMills


    val now = System.currentTimeMillis()
    val diff = now - mills
    val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())

    return when {
        diff < minuteMills -> context.getString(R.string.just_now)
        diff < 2 * minuteMills -> context.getString(R.string.one_minute)
        diff < 50 * minuteMills -> "${diff / minuteMills} ${context.getString(R.string.minute)}"
        diff < 90 * minuteMills -> context.getString(R.string.one_hour)
        diff < 24 * hourMills -> "${diff / hourMills} ${context.getString(R.string.hours)}"
        diff < 48 * hourMills -> context.getString(R.string.yesterday)
        diff < 7 * dayMills -> "${diff / dayMills} ${context.getString(R.string.days)}"
        else -> return sdf.format(Date(mills))
    }
}