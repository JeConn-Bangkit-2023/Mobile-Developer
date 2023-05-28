package com.capstone.jeconn.data.entities

// key: UID
data class PrivateDataEntity(
    val email: String,
    val username: String,
    val created_date: Long,
    val notifications: List<Notification>? = null,
    val messages_room_id: List<Int>? = null,
    val invoice_id: List<Int>? = null,
)

data class Notification(
    val date: Long,
    val subject: String,
    val description: String,
)