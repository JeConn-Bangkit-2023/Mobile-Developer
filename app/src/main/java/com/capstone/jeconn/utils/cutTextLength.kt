package com.capstone.jeconn.utils

fun cutTextLength(text: String, maxLength: Int): String {
    val messageWithEllipsis = if (text.length > maxLength) {
        text.take(maxLength) + "..."
    } else {
        text
    }
    return messageWithEllipsis
}