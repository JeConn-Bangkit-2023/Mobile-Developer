package com.capstone.jeconn.data.entities

// Private Data
// key: UID
data class PrivateDataEntity(
    val username: String,
    val created_date: Long,
    val messages_room_id: List<Long>? = null,
    val notifications: List<Notification>? = null,
    val payments_id: List<Long>? = null
)

// key: Randomize by Firebase
data class Notification(
    val date: Long,
    val subject: String,
    val description: String,
)