package com.capstone.jeconn.data.entities

// key: message_room_id (Int)
data class MessageRoomEntity(
    val currentTargetName: String? = null,
    val currentTargetImageUrl: String? = null,
    val messages_room_id: Long? = null,
    val members_username: Map<String, String>? = null,
    val messages: Map<String, Message>? = null
)

data class Message(
    val date: Long? = null,
    val username: String? = null,
    val message: String? = null,
    val image_url: String? = null,
    val invoice_id: Long? = null
)