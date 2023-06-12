package com.capstone.jeconn.data.entities

// key: username
data class PublicDataEntity(
    val username: String? = null,
    val full_name: String? = null,
    val profile_image_url: String? = null,
    val detail_information: DetailInformation? = null,
    val jobInformation: JobInformation? = null,
    val vacanciesPostId: Map<String, Int>? = null,
    val notifications: List<Notification>? = null,
    val messages_room_id: Map<String, Int>? = null,
    val invoice_id: Map<String, Int>? = null,
    val myLocation: LocationEntity? = null
)

data class DetailInformation(
    val about_me: String? = null,
    val date_of_birth: Long? = null,
    val gender: String? = null,
)

data class JobInformation(
    val categories: List<Int>? = null,
    val imagesUrl: Map<String, Image>? = null,
    @field:JvmField  val isOpenToOffer: Boolean = false,
    val location: LocationEntity? = null
)

data class Image(
    val post_image_uid: String? = null,
    val post_image_url:String? = null
)
data class Notification(
    val date: Long? = null,
    val subject: String? = null,
    val description: String? = null,
)