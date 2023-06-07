package com.capstone.jeconn.data.entities

// key: UID
data class PrivateDataEntity(
    val email: String? = null,
    val username: String? = null,
    val created_date: Long? = null,
    val notifications: List<Notification>? = null,
    val messages_room_id: List<Int>? = null,
    val invoice_id: List<Int>? = null,
)

data class Notification(
    val date: Long? = null,
    val subject: String? = null,
    val description: String? = null,
)