package com.capstone.jeconn.repository

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.state.UiState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileRepository(
    private val context: Context
) {
    val res = Firebase.database.reference
    private val auth = Firebase.auth

    val shortInfoState: MutableState<UiState<PublicDataEntity>> = mutableStateOf(UiState.Empty)

    fun getShortData() {
        shortInfoState.value = UiState.Loading

        res.child("publicData").child(auth.currentUser!!.displayName!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(PublicDataEntity::class.java)
                    if (data != null) {
                        shortInfoState.value = UiState.Success(data)
                    } else {
                        Log.e("onNull", "publicData == null")
                        shortInfoState.value = UiState.Error("Null data")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    shortInfoState.value = UiState.Error(error.message)
                    Log.e("onCanceled", error.message)
                }

            })
    }
}