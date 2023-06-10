package com.capstone.jeconn.utils

import android.content.Context
import com.capstone.jeconn.R
import java.util.concurrent.TimeUnit

fun calculateDuration(context: Context, start: Long, end: Long): String {
    val diffInMillis = start * 1000 - start * 1000
    val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)

    return when {
        diffInMinutes >= 60 -> {
            val diffInHours = diffInMinutes / 60
            when {
                diffInHours >= 24 -> {
                    val diffInDays = diffInHours / 24
                    when {
                        diffInDays >= 7 -> {
                            val diffInWeeks = diffInDays / 7
                            when {
                                diffInWeeks >= 4 -> {
                                    val diffInMonths = diffInWeeks / 4
                                    when {
                                        diffInMonths >= 12 -> {
                                            val diffInYears = diffInMonths / 12
                                            "$diffInYears ${context.getString(R.string.years)}"
                                        }
                                        else -> "$diffInMonths ${context.getString(R.string.months)}"
                                    }
                                }
                                else -> "$diffInWeeks ${context.getString(R.string.weeks)}"
                            }
                        }
                        else -> "$diffInDays ${context.getString(R.string.days)}"
                    }
                }
                else -> "$diffInHours ${context.getString(R.string.hours)}"
            }
        }
        else -> "10 ${context.getString(R.string.minute)}"
    }
}