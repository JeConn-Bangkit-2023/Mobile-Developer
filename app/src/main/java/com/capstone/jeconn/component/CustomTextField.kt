package com.capstone.jeconn.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.capstone.jeconn.R

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    length: Int = 50,
    maxLine: Int = Int.MAX_VALUE,
    type: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    state: MutableState<String>,
    rounded: Dp = 8.dp
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = state.value,
        onValueChange = { text ->
            if (text.length <= length) {
                state.value = text
            }
            if (type == KeyboardType.Number ||
                type == KeyboardType.NumberPassword || type == KeyboardType.Decimal
            ) {
                val filteredValue = text.filter { it.isDigit() }
                state.value = filteredValue
            }
        },
        label = {
            Text(text = label)
        },
        modifier = modifier,
        shape = RoundedCornerShape(rounded),
        trailingIcon =
        {
            if (state.value != "") {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            state.value = ""
                        },
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = type,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        maxLines = maxLine
    )
}