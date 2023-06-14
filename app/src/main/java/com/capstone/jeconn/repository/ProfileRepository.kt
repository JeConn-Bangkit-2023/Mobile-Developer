package com.capstone.jeconn.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.capstone.jeconn.R
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.listener.ImageProcessingListener
import com.capstone.jeconn.listener.ImageProcessor
import com.capstone.jeconn.retrofit.ApiConfig
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.createImageAsFormReqBody
import com.capstone.jeconn.utils.uriToFile
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository(
    private val context: Context
) {
    private val ref = Firebase.database.reference
    private val auth = Firebase.auth
    private val uploadImageApiService = ApiConfig.uploadImageApiService

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

    fun updateJobImage(uri: Uri) {
        updateJobImageState.value = UiState.Loading

        CoroutineScope(Dispatchers.Default).launch {
            val myFile = uriToFile(uri, context)
            if (myFile.exists()) {
                ImageProcessor(context, myFile, object : ImageProcessingListener {
                    override fun onImageSafe() {

                        val profileImage = createImageAsFormReqBody(myFile, "image")
                        val response =
                            uploadImageApiService.postJobImage(auth.currentUser!!.displayName!!, profileImage)

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

                    override fun onImageUnsafe(errorMessage: String) {
                        updateJobImageState.value = UiState.Error(errorMessage)
                    }

                    override fun onBadRequest(errorMessage: String) {
                        updateJobImageState.value = UiState.Error(errorMessage)
                    }

                    override fun onServerError(errorMessage: String) {
                        updateJobImageState.value = UiState.Error(errorMessage)
                    }

                })
            }
        }


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

    fun updateProfileImage(uri: Uri) {
        updateProfileImageState.value = UiState.Loading

        CoroutineScope(Dispatchers.Default).launch {
            val myFile = uriToFile(uri, context)
            if (myFile.exists()) {
                ImageProcessor(context, myFile, object : ImageProcessingListener {
                    override fun onImageSafe() {
                        val profileImage =
                            createImageAsFormReqBody(myFile,"profile_image")

                        val uploadResponse =
                            uploadImageApiService.updateProfileImage(
                                auth.currentUser!!.displayName!!,
                                profileImage
                            )

                        uploadResponse.enqueue(object : Callback<Unit> {
                            override fun onResponse(
                                call: Call<Unit>,
                                response: Response<Unit>
                            ) {
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
                                updateProfileImageState.value =
                                    UiState.Error(t.message.toString())
                            }

                        })
                    }

                    override fun onImageUnsafe(errorMessage: String) {
                        updateProfileImageState.value =
                            UiState.Error(errorMessage)
                    }

                    override fun onBadRequest(errorMessage: String) {
                        updateProfileImageState.value =
                            UiState.Error(errorMessage)
                    }

                    override fun onServerError(errorMessage: String) {
                        updateProfileImageState.value =
                            UiState.Error(errorMessage)
                    }

                })
            }
        }
    }
}