package com.capstone.jeconn.data.entities

// key: username
data class PublicDataEntity(
    val username: String? = null,
    val full_name: String? = null,
    val profile_image_url: String? = null,
    val detail_information: DetailInformation? = null,
    val jobInformation: JobInformation? = null,
    val vacanciesPostId: List<Int>? = null
)

data class DetailInformation(
    val about_me: String? = null,
    val date_of_birth: Long? = null,
    val gender: String? = null,
)

data class JobInformation(
    val categories: List<Int>? = null,
    val imagesUrl: List<ImageList>? = null,
    val isOpen: Boolean? = null,
    val location: LocationEntity? = null
)

data class ImageList(
    val post_image_uid: Long? = null,
    val post_image_url:String? = null
)