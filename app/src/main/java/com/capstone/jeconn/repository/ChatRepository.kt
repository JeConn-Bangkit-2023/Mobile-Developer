package com.capstone.jeconn.repository

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.capstone.jeconn.R
import com.capstone.jeconn.data.entities.Message
import com.capstone.jeconn.data.entities.MessageRoomEntity
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.asMap
import com.capstone.jeconn.utils.getRandomNumeric
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatRepository(private val context: Context) {

    private val auth = Firebase.auth.currentUser!!
    private val ref = Firebase.database.reference

    val openChatChatState: MutableState<UiState<Long>> = mutableStateOf(UiState.Empty)
    val loadMessageState: MutableState<UiState<MessageRoomEntity>> = mutableStateOf(UiState.Empty)
    val loadMessageListState: MutableState<UiState<MutableList<MessageRoomEntity>>> =
        mutableStateOf(UiState.Empty)

    fun openChat(myUsername: String, targetUsername: String) {

        openChatChatState.value = UiState.Loading

        ref.child("publicData").get().addOnSuccessListener { publicData ->

            val isChatExist = publicData.child(myUsername)
                .child("messages_room_id").children.any { myId ->
                    publicData.child(targetUsername).child("messages_room_id")
                        .hasChild(myId.key.toString())
                }
            if (!isChatExist) {
                val generateId = getRandomNumeric()
                ref.child("publicData").child(myUsername).child("messages_room_id")
                    .child(generateId.toString()).setValue(generateId).addOnSuccessListener {
                        ref.child("publicData").child(targetUsername).child("messages_room_id")
                            .child(generateId.toString()).setValue(generateId)

                        val newMessage = MessageRoomEntity(
                            members_username = mapOf(
                                myUsername to myUsername,
                                targetUsername to targetUsername,
                            ),
                            messages_room_id = generateId
                        )
                        ref.child("messageRoomList").child(generateId.toString())
                            .updateChildren(
                                newMessage.asMap()
                            ).addOnSuccessListener {
                                //Success
                                openChatChatState.value = UiState.Success(generateId)
                            }.addOnFailureListener { task ->
                                Log.e("fail create", task.message.toString())
                                openChatChatState.value = UiState.Error(task.message.toString())
                            }

                    }.addOnFailureListener { task ->
                        Log.e("add target", task.message.toString())
                        openChatChatState.value = UiState.Error(task.message.toString())
                    }.addOnFailureListener { task ->
                        Log.e("add mine", task.message.toString())
                        openChatChatState.value = UiState.Error(task.message.toString())
                    }
            } else {
                publicData.child(myUsername)
                    .child("messages_room_id").children.forEach { myRoomChatList ->
                        publicData.child(targetUsername)
                            .child("messages_room_id").children.forEach { targetRoomChatList ->
                                if (myRoomChatList.key == targetRoomChatList.key) {
                                    openChatChatState.value =
                                        UiState.Success(targetRoomChatList.key!!.toLong())
                                }
                            }
                    }
            }
        }
    }

    fun loadChat(roomChatId: String) {
        loadMessageState.value = UiState.Loading
        ref.child("messageRoomList").child(roomChatId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val loadMessageData =
                        snapshot.getValue(MessageRoomEntity::class.java) ?: MessageRoomEntity()
                    loadMessageState.value = UiState.Success(loadMessageData)
                }

                override fun onCancelled(error: DatabaseError) {
                    loadMessageState.value = UiState.Error(context.getString(R.string.server_fail))
                    Log.e("loadMessage", error.message)
                }

            })
    }

    fun sendMessage(roomChatId: String, message: Message) {
        val newMessage = mapOf(System.currentTimeMillis().toString() to message)
        ref.child("messageRoomList").child(roomChatId).child("messages").updateChildren(newMessage)
    }

    fun loadMessageList() {
        loadMessageListState.value = UiState.Loading
        val messageList = mutableStateListOf<MessageRoomEntity>()
        ref.child("publicData").child(auth.displayName!!).child("messages_room_id").get()
            .addOnSuccessListener { myMessageList ->
                val myMessageListCount = myMessageList.childrenCount

                ref.child("messageRoomList").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChildren()) {
                            snapshot.children.forEach { message ->
                                val eachMessage = message.getValue(MessageRoomEntity::class.java)!!

                                val targetUsername =
                                    eachMessage.members_username!!.keys.find { it != auth.displayName }

                                if (targetUsername != null) {
                                    ref.child("publicData").child(targetUsername).get()
                                        .addOnSuccessListener { targetSnapshot ->
                                            val newMessageRoomEntity = MessageRoomEntity(
                                                currentTargetName = targetSnapshot.child("full_name")
                                                    .getValue(String::class.java),
                                                currentTargetImageUrl = targetSnapshot.child("profile_image_url")
                                                    .getValue(String::class.java),
                                                messages_room_id = eachMessage.messages_room_id,
                                                members_username = eachMessage.members_username,
                                                messages = eachMessage.messages
                                            )
                                            messageList.add(newMessageRoomEntity)
                                            if (messageList.size.toLong() == myMessageListCount) {
                                                loadMessageListState.value =
                                                    UiState.Success(messageList)

                                            }
                                        }
                                } else {
                                    Log.e(
                                        "targetUsername",
                                        "Target username is null, cannot get name and image!"
                                    )
                                }
                            }
                        } else {
                            loadMessageListState.value = UiState.Success(messageList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        loadMessageListState.value =
                            UiState.Error(context.getString(R.string.server_fail))
                        Log.e("loadMessageList", error.message)
                    }

                })
            }

    }
}