package com.capstone.jeconn.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.jeconn.R
import com.capstone.jeconn.data.entities.InvoiceEntity
import com.capstone.jeconn.utils.calculateDuration
import com.capstone.jeconn.utils.getStatus

@Composable
fun CustomInvoiceCard(
    modifier: Modifier = Modifier,
    invoice: InvoiceEntity
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .clickable {
                //TODO
            }
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(12.dp)
            .fillMaxWidth()

    ) {
        Row {
            Text(
                text = "${context.getString(R.string.invoice_id)}: ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = invoice.invoice_id.toString(),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }

        Row {
            Text(
                text = "${context.getString(R.string.service)}: ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = invoice.service!!,
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }

        Row {
            Text(
                text = "${context.getString(R.string.length_of_working_time)}: ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = calculateDuration(context, invoice.start_date!!, invoice.end_date!!),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }

        Row {
            Text(
                text = "${context.getString(R.string.price)}: ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = "Rp.- ${invoice.price.toString()}",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }

        Row {
            Text(
                text = "${context.getString(R.string.status)}: ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Text(
                text = getStatus(context, invoice.status!!),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }

        Text(
            text = "Detail ->",
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .align(End)
        )
    }
}