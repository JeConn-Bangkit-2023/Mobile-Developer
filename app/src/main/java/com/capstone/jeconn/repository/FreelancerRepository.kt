package com.capstone.jeconn.repository

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.capstone.jeconn.R
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.state.UiState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FreelancerRepository(
    private val context: Context
) {
    private val ref = Firebase.database.reference
    private val auth = Firebase.auth.currentUser!!

    val loadFreelancerListState: MutableState<UiState<MutableList<PublicDataEntity>>> =
        mutableStateOf(UiState.Loading)
    val loadFreelancerState: MutableState<UiState<PublicDataEntity>> =
        mutableStateOf(UiState.Loading)

    fun loadFreelancerList() {
        loadFreelancerListState.value = UiState.Loading
        val tempFreelanceList = mutableListOf<PublicDataEntity>()
        ref.child("publicData").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val myLocation = snapshot.child(auth.displayName.toString()).child("jobInformation")
                    .child("location").getValue(LocationEntity::class.java)
                val publicDataListCount = snapshot.childrenCount.toInt()
                if (publicDataListCount != 0) {
                    snapshot.children.forEach { eachData ->
                        val getData = eachData.getValue(PublicDataEntity::class.java)
                        val newData = PublicDataEntity(
                            username = getData?.username,
                            full_name = getData?.full_name,
                            profile_image_url = getData?.profile_image_url,
                            detail_information = getData?.detail_information,
                            jobInformation = getData?.jobInformation,
                            vacanciesPostId = getData?.vacanciesPostId,
                            notifications = getData?.notifications,
                            messages_room_id = getData?.messages_room_id,
                            invoice_id = getData?.invoice_id,
                            myLocation = myLocation
                        )
                        tempFreelanceList.add(newData)
                    }

                    if (publicDataListCount == tempFreelanceList.size) {
                        loadFreelancerListState.value = UiState.Success(tempFreelanceList)
                    }
                } else {
                    loadFreelancerListState.value =
                        UiState.Error(context.getString(R.string.empty_data))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                loadFreelancerListState.value =
                    UiState.Error(context.getString(R.string.server_fail))
            }

        })
    }

    fun loadFreelancer(username: String) {
        loadFreelancerState.value = UiState.Loading
        ref.child("publicData").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val myLocation = snapshot.child(auth.displayName.toString()).child("jobInformation")
                    .child("location").getValue(LocationEntity::class.java)

                val getData = snapshot.child(username).getValue(PublicDataEntity::class.java)
                val newData = PublicDataEntity(
                    username = getData?.username,
                    full_name = getData?.full_name,
                    profile_image_url = getData?.profile_image_url,
                    detail_information = getData?.detail_information,
                    jobInformation = getData?.jobInformation,
                    vacanciesPostId = getData?.vacanciesPostId,
                    notifications = getData?.notifications,
                    messages_room_id = getData?.messages_room_id,
                    invoice_id = getData?.invoice_id,
                    myLocation = myLocation
                )

                loadFreelancerState.value = UiState.Success(newData)
            }

            override fun onCancelled(error: DatabaseError) {
                loadFreelancerListState.value =
                    UiState.Error(context.getString(R.string.server_fail))
            }

        })
    }
}