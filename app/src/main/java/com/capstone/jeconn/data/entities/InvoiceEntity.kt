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
    val invoice_id: Int? = null,
    val freelancer_username: String? = null,
    val tenant_username: String? = null,
    val status: Int? = null,
    val service: String? = null,
    val created_date: String? = null,
    val start_date: Long? = null,
    val end_date: Long? = null,
    val price: Long? = null,
    val description: String? = null,
    val note: String? = null
)
