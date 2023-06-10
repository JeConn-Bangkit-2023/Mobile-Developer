package com.capstone.jeconn.component.message_item.type

import androidx.compose.runtime.Composable
import com.capstone.jeconn.data.entities.InvoiceEntity

@Composable
fun CustomMessageItem(
    senderProfileImageUrl: String,
    text: String? = null,
    imageUrl: String? = null,
    invoice: InvoiceEntity? = null,
    isMine: Boolean,
    dateTime: Long,
) {
    when {
        (text != null) -> {
            MessageText(
                senderProfileImageUrl = senderProfileImageUrl,
                text = text,
                isMine = isMine,
                dateTime = dateTime
            )
        }

        (imageUrl != null) -> {
            MessageImage(
                senderProfileImageUrl = senderProfileImageUrl,
                imageUrl = imageUrl,
                isMine = isMine,
                dateTime = dateTime
            )
        }

        (invoice != null) -> {
            MessageInvoice(
                senderProfileImageUrl = senderProfileImageUrl,
                invoice = invoice,
                isMine = isMine,
                dateTime = dateTime
            )
        }
    }
}