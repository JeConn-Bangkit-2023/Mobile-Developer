package com.capstone.jeconn.data.entities

// Public Data
// key: username
data class PublicDataEntity(
    val username: String,
    val email: String,
    val full_name: String,
    val profile_image_url: String,
    val detail_information: DetailInformation? = null,
    val jobInformation: JobInformation? = null
)

data class DetailInformation(
    val about_me: String,
    val date_of_birth: Long,
    val gender: String,
    val province: String,
    val city: String,
    val district: String,
)

data class JobInformation(
    val categories: List<String>,
    val skills: List<String>,
    val imagesUrl: List<String>,
    val isOpen: Boolean
)