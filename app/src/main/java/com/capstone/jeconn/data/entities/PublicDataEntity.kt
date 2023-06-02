package com.capstone.jeconn.data.entities

import com.google.android.gms.maps.model.LatLng

// key: username
data class PublicDataEntity(
    val phoneNumber: String,
    val username: String,
    val full_name: String,
    val profile_image_url: String,
    val detail_information: DetailInformation? = null,
    val jobInformation: JobInformation? = null,
    val vacanciesPostId: List<Int>? = null
)

data class DetailInformation(
    val about_me: String,
    val date_of_birth: Long,
    val gender: String,
)

data class JobInformation(
    val categories: List<Int>,
    val skills: List<String>,
    val imagesUrl: List<String>,
    val isOpen: Boolean,
    val location: LatLng? = null
)