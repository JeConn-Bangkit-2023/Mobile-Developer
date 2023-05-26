package com.capstone.jeconn.data.entities

// key: message_room_id
data class MessageRoomEntity(
    val members_username: List<String>,
    val messages: List<Message>
)

data class Message(
    val date: Long,
    val sender_name: String,
    val message: String? = null,
    val invoice_id: Long? = null
)