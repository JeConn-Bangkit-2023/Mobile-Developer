package com.capstone.jeconn.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.capstone.jeconn.R
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.retrofit.ApiConfig
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.reduceFileImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileRepository(
    private val context: Context
) {
    private val ref = Firebase.database.reference
    private val auth = Firebase.auth
    private val apiService = ApiConfig.apiService
    val getPublicDataState: MutableState<UiState<PublicDataEntity>> = mutableStateOf(UiState.Empty)
    val updateProfileImageState: MutableState<UiState<String>> = mutableStateOf(UiState.Empty)
    val updateDetailInfoState: MutableState<UiState<String>> =
        mutableStateOf(UiState.Empty)
    val updateJobImageState: MutableState<UiState<String>> = mutableStateOf(UiState.Empty)


    fun updateDetailInfo(
        dob: Long,
        gender: String,
        aboutMe: String,
        listCategory: List<Int>,
        offers: Boolean
    ) {
        updateDetailInfoState.value = UiState.Loading

        val newInfoData = mapOf(
            "about_me" to aboutMe,
            "date_of_birth" to dob,
            "gender" to gender,
        )

        val newJobData = mapOf(
            "categories" to listCategory,
            "isOpenToOffer" to offers
        )



        ref.child("publicData").child(auth.currentUser!!.displayName!!).child("jobInformation")
            .updateChildren(newJobData).addOnSuccessListener {
                ref.child("publicData").child(auth.currentUser!!.displayName!!)
                    .child("detail_information").updateChildren(newInfoData).addOnSuccessListener {
                        updateDetailInfoState.value =
                            UiState.Success(context.getString(R.string.successfully_update_information))
                    }.addOnFailureListener {
                        updateDetailInfoState.value = UiState.Error(it.message.toString())
                    }
            }.addOnFailureListener {
                updateDetailInfoState.value = UiState.Error(it.message.toString())
            }

    }

    fun updateJobImage(file: File) {
        updateJobImageState.value = UiState.Loading

        val myFile = reduceFileImage(file)
        val fileRequestBody = myFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val profileImage: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", file.name, fileRequestBody)
        val response =
            apiService.postJobImage(auth.currentUser!!.displayName!!, profileImage)

        response.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                when (response.code()) {
                    201 -> {
                        updateJobImageState.value =
                            UiState.Success(context.getString(R.string.successfully_update_job_image))
                    }

                    400 -> {
                        updateJobImageState.value =
                            UiState.Error(context.getString(R.string.image_type_wrong))
                    }

                    else -> {
                        updateJobImageState.value =
                            UiState.Error(context.getString(R.string.server_fail))
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                updateJobImageState.value = UiState.Error(t.message.toString())
            }

        })

    }

    fun getPublicData() {
        getPublicDataState.value = UiState.Loading

        ref.child("publicData").child(auth.currentUser!!.displayName!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(PublicDataEntity::class.java)
                    if (data != null) {
                        getPublicDataState.value = UiState.Success(data)
                    } else {
                        Log.e("onNull", "publicData == null")
                        getPublicDataState.value = UiState.Error("Null data")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    getPublicDataState.value = UiState.Error(error.message)
                    Log.e("onCanceled", error.message)
                }

            })
    }

    @SuppressLint("ResourceType")
    fun updateProfileImage(file: File) {
        updateProfileImageState.value = UiState.Loading
        val myFile = reduceFileImage(file)
        val fileRequestBody = myFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val profileImage: MultipartBody.Part =
            MultipartBody.Part.createFormData("profile_image", file.name, fileRequestBody)
        val response =
            apiService.updateProfileImage(auth.currentUser!!.displayName!!, profileImage)

        response.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                when (response.code()) {
                    200 -> {
                        updateProfileImageState.value =
                            UiState.Success(context.getString(R.string.successfully_update_profile_image))
                    }

                    400 -> {
                        updateProfileImageState.value =
                            UiState.Error(context.getString(R.string.image_type_wrong))
                    }

                    else -> {
                        updateProfileImageState.value =
                            UiState.Error(context.getString(R.string.server_fail))
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                updateProfileImageState.value = UiState.Error(t.message.toString())
            }

        })
    }
}