package com.capstone.jeconn.data.dummy

import com.capstone.jeconn.data.entities.DetailInformation
import com.capstone.jeconn.data.entities.InvoiceEntity
import com.capstone.jeconn.data.entities.JobInformation
import com.capstone.jeconn.data.entities.Message
import com.capstone.jeconn.data.entities.MessageRoomEntity
import com.capstone.jeconn.data.entities.Notification
import com.capstone.jeconn.data.entities.PrivateDataEntity
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.data.entities.VacanciesEntity

object DummyData {
    const val UID = "Usk2d7C0jzMYBK4WlqwjGiRtf6A3"

    private val notificationList: List<Notification> = listOf(
        Notification(1621962000, "Pemberitahuan 1", "Ini adalah pemberitahuan 1"),
        Notification(1622048400, "Pemberitahuan 2", "Ini adalah pemberitahuan 2"),
        Notification(1622134800, "Pemberitahuan 3", "Ini adalah pemberitahuan 3")
    )

    val messageList: List<MessageRoomEntity> = listOf(
        MessageRoomEntity(
            listOf("user1", "user2"),
            listOf(
                Message(1621962000, "user1", "Halo", null),
                Message(1622048400, "user2", "Halo juga", null),
                Message(1622134800, "user1", "Ada yang bisa dibantu?", null)
            )
        ),
        MessageRoomEntity(
            listOf("user1", "user3"),
            listOf(
                Message(1621962000L, "user1", "Halo", null),
                Message(1622048400, "user3", "Halo juga", null),
                Message(1622134800, "user1", "Ada yang bisa dibantu?", null)
            )
        )
    )

    val invoiceList: List<InvoiceEntity> = listOf(
        InvoiceEntity(
            1,
            "fauzanramadhani06",
            "billy01",
            0,
            "Cleaning",
            "2023-05-01",
            1651536000,
            1651622400,
            100000,
            "Cleaning service for the apartment",
            "Please bring your own cleaning supplies"
        ),
        InvoiceEntity(
            2,
            "fauzanramadhani06",
            "billy01",
            1,
            "Plumbing",
            "2023-05-02",
            1651622400,
            1651708800,
            150000,
            "Fixing leaky pipes in the kitchen",
            null
        ),
        InvoiceEntity(
            3,
            "fauzanramadhani06",
            "billy01",
            4,
            "Gardening",
            "2023-05-03",
            1651708800,
            1651795200,
            80000,
            "Maintaining the backyard garden",
            null
        )
    )

    val publicData: List<PublicDataEntity> = listOf(
        PublicDataEntity(
            username = "user1",
            full_name = "John Doe",
            profile_image_url = "https://cdns.klimg.com/kapanlagi.com/p/ukinoah051.jpg",
            detail_information = DetailInformation(
                about_me = "I'm a software engineer",
                date_of_birth = 946684800, // Contoh tanggal lahir: 1 Januari 2000
                gender = "Male",
                province = "California",
                city = "Los Angeles",
                district = "Downtown"
            ),
            jobInformation = JobInformation(
                categories = listOf("IT", "Software Development"),
                skills = listOf(
                    "Kotlin",
                    "JavaScript",
                    "Pengisi Acara",
                    "Gitaris",
                    "Cosplayer",
                    "Model",
                    "Kotlin",
                    "JavaScript",
                    "Pengisi Acara",
                    "Gitaris",
                    "Cosplayer",
                    "Model"
                ),
                imagesUrl = listOf(
                    "https://cdns.klimg.com/kapanlagi.com/p/maliqanddessentials540.jpg",
                    "https://cdns.klimg.com/kapanlagi.com/p/ukinoah051.jpg"
                ),
                isOpen = true
            )
        ),
        PublicDataEntity(
            username = "user2",
            full_name = "Jane Smith",
            profile_image_url = "https://cdns.klimg.com/kapanlagi.com/p/ukinoah051.jpg",
            detail_information = DetailInformation(
                about_me = "I'm a graphic designer",
                date_of_birth = 915148800, // Contoh tanggal lahir: 1 Januari 1999
                gender = "Female",
                province = "New York",
                city = "Manhattan",
                district = "Midtown"
            ),
            jobInformation = JobInformation(
                categories = listOf("Design", "Art"),
                skills = listOf("Photoshop", "Illustrator"),
                imagesUrl = listOf(
                    "https://cdns.klimg.com/kapanlagi.com/p/maliqanddessentials540.jpg",
                    "https://cdns.klimg.com/kapanlagi.com/p/ukinoah051.jpg"
                ),
                isOpen = false
            )
        )
    )

    fun privateData(): List<PrivateDataEntity> {
        val dataDummy: MutableList<PrivateDataEntity> = mutableListOf()

        for (i in 1..10) {
            dataDummy.add(
                PrivateDataEntity(
                    username = "fauzanramadhani06",
                    created_date = 1685098422000 - (i * 40000),
                    messages_room_id = listOf(0, 1),
                    notifications = notificationList,
                    payments_id = listOf(0, 1)
                )
            )
        }
        return dataDummy
    }

    val vacanciesList: List<VacanciesEntity> = listOf(
        VacanciesEntity(
            username = "user1",
            timestamp = 1685130941,
            city = "Jakarta",
            salary = 5000000,
            category = listOf("IT", "Programming"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user2",
            timestamp = 1685130729,
            city = "Surabaya",
            salary = 7000000,
            category = listOf("Finance", "Accounting"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user2",
            timestamp = 1685130729,
            city = "Bandung",
            salary = 6000000,
            category = listOf("Marketing", "Sales"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user1",
            timestamp = 1685130941,
            city = "Jakarta",
            salary = 5000000,
            category = listOf("IT", "Programming"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user2",
            timestamp = 1685130729,
            city = "Surabaya",
            salary = 7000000,
            category = listOf("Finance", "Accounting"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user2",
            timestamp = 1685130729,
            city = "Bandung",
            salary = 6000000,
            category = listOf("Marketing", "Sales"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user1",
            timestamp = 1685130941,
            city = "Jakarta",
            salary = 5000000,
            category = listOf("IT", "Programming"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user2",
            timestamp = 1685130729,
            city = "Surabaya",
            salary = 7000000,
            category = listOf("Finance", "Accounting"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user2",
            timestamp = 1685130729,
            city = "Bandung",
            salary = 6000000,
            category = listOf("Marketing", "Sales"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user1",
            timestamp = 1685130941,
            city = "Jakarta",
            salary = 5000000,
            category = listOf("IT", "Programming"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user2",
            timestamp = 1685130729,
            city = "Surabaya",
            salary = 7000000,
            category = listOf("Finance", "Accounting"),
            description = "Lorem ipsum dolor sit amet"
        ),
        VacanciesEntity(
            username = "user2",
            timestamp = 1685130729,
            city = "Bandung",
            salary = 6000000,
            category = listOf("Marketing", "Sales"),
            description = "Lorem ipsum dolor sit amet"
        ),
        // Tambahkan data lain sesuai kebutuhan Anda
    )

}