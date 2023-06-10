package com.capstone.jeconn.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.utils.patternNoHours
import com.capstone.jeconn.utils.patternWithHours
import com.capstone.jeconn.utils.timeStampToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerTextField(
    state: MutableState<Long>,
    withHours: Boolean,
    range: IntRange,
    label: String,
    navHostController: NavHostController
) {
    val pattern = if (withHours) patternWithHours else patternNoHours
    val pickerState = rememberSaveable {
        mutableStateOf(false)
    }

    BackHandler {
        if (pickerState.value) {
            pickerState.value = false
        } else {
            navHostController.navigateUp()
        }
    }

    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
    ) {

        if (pickerState.value) {
            focusManager.clearFocus()
            Popup {
                DateTimeWheelPicker(dateTime = state, withHours, range) {
                    pickerState.value = false
                }
            }
        }

        OutlinedTextField(
            readOnly = true,
            value = timeStampToDate(pattern, state.value),
            onValueChange = { pickerState.value = true },
            label = { Text(text = label) },
            trailingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            pickerState.value = true
                        }
                )
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        pickerState.value = !pickerState.value
                    }
                }
        )

    }
}