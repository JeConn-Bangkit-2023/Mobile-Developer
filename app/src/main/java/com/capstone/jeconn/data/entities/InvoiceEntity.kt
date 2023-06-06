package com.capstone.jeconn.data.entities

//key: invoice_id (Int)
data class InvoiceEntity(
    /*
    Status code:
    0 = Need to pay
    1 = Already paid
    2 = Deferred
    3 = Awaiting disbursement
    4 = Finish
    5 = Canceled
    */
    val invoice_id: Int,
    val freelancer_username: String,
    val tenant_username: String,
    val status: Int = 0,
    val service: String,
    val created_date: String,
    val start_date: Long,
    val end_date: Long,
    val price: Long,
    val description: String,
    val note: String? = null
)
