package com.capstone.jeconn.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.capstone.jeconn.R
import com.capstone.jeconn.utils.dateToTimeStamp
import com.capstone.jeconn.utils.patternNoHours
import com.capstone.jeconn.utils.patternWithHours
import com.capstone.jeconn.utils.timeStampToDate
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DateTimeWheelPicker(
    dateTime: MutableState<Long>,
    withHours: Boolean,
    yearRange: IntRange,
    onChange: () -> Unit = {},
) {

    val pattern = if (withHours) patternWithHours else patternNoHours
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (withHours) {
            WheelDateTimePicker(
                startDateTime = LocalDateTime.parse(
                    timeStampToDate(pattern, dateTime.value),
                    DateTimeFormatter.ofPattern(pattern)
                ),
                yearsRange = yearRange,
                timeFormat = TimeFormat.HOUR_24,
                size = DpSize(350.dp, 200.dp),
                rowCount = 3,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textColor = MaterialTheme.colorScheme.onBackground,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = true,
                    shape = RoundedCornerShape(5.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                )
            ) { snappedDateTime ->
                val formatter = DateTimeFormatter.ofPattern(pattern)
                val result = snappedDateTime.format(formatter)
                dateTime.value = dateToTimeStamp(pattern, result, true)
            }
        } else {
            WheelDatePicker(
                startDate = LocalDate.parse(
                    timeStampToDate(pattern, dateTime.value),
                    DateTimeFormatter.ofPattern(pattern)
                ),
                yearsRange = yearRange,
                size = DpSize(350.dp, 200.dp),
                rowCount = 3,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textColor = MaterialTheme.colorScheme.onBackground,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = true,
                    shape = RoundedCornerShape(5.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                )
            ) { snappedDateTime ->
                val formatter = DateTimeFormatter.ofPattern(pattern)
                val result = snappedDateTime.format(formatter)
                dateTime.value = dateToTimeStamp(pattern, result, false)
            }
        }

        CustomButton(
            text = context.getString(R.string.update),
            modifier = Modifier
                .size(DpSize(350.dp, 60.dp))
                .padding(vertical = 10.dp)
        ) {
            onChange()
        }
    }
}