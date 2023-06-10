package com.capstone.jeconn.data.entities

// key: message_room_id (Int)
data class MessageRoomEntity(
    val members_username: List<String>? = null,
    val messages: List<Message>? = null
)

data class Message(
    val date: Long? = null,
    val username: String? = null,
    val message: String? = null,
    val image_url: String? = null,
    val invoice_id: Long? = null
)