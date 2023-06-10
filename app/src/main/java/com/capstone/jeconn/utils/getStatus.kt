package com.capstone.jeconn.utils

import android.content.Context
import com.capstone.jeconn.R

fun getStatus(context: Context, status: Int): String {

    /*
   Status code:
   0 = Need to pay
   1 = Already paid
   2 = Deferred
   3 = Awaiting disbursement
   4 = Finish
   5 = Canceled
   */
    return when (status) {
        0 -> context.getString(R.string.unpaid)
        1, 2 -> context.getString(R.string.process)
        3 -> context.getString(R.string.waiting)
        4 -> context.getString(R.string.done)
        else -> context.getString(R.string.canceled)
    }
}