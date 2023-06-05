package com.capstone.jeconn.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.capstone.jeconn.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    stateHolder: DropDownStateHolder,
    label: String,
    modifier: Modifier = Modifier

) {
    val focusManager = LocalFocusManager.current

    if (stateHolder.enabled) {
        focusManager.clearFocus()
    }

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = stateHolder.value,
            onValueChange = { },
            label = {
                Text(text = label)
            },
            trailingIcon = {
                Icon(
                    painterResource(id = stateHolder.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            stateHolder.onEnabled(!(stateHolder.enabled))
                        }
                )
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = modifier
                .onGloballyPositioned {
                    stateHolder.onSize(it.size.toSize())
                }
                .onFocusChanged {
                    if (it.isFocused) {
                        stateHolder.onEnabled(!(stateHolder.enabled))
                    }
                }
        )

        DropdownMenu(
            expanded = stateHolder.enabled,
            onDismissRequest = { stateHolder.onEnabled(false) },
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .width(with(LocalDensity.current) { stateHolder.size.width.toDp() })
        ) {
            stateHolder.items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    onClick = {
                        stateHolder.onSelectedIndex(index)
                        stateHolder.onEnabled(false)
                    },
                    text = {
                        Text(text = s)
                    }
                )
            }
        }
    }
}

class DropDownStateHolder(currentState: String, listItem: List<String>) {


    var enabled by mutableStateOf(false)
    var value by mutableStateOf(currentState)
    val items = listItem
    var selectedndex by mutableStateOf(-1)
    var size by mutableStateOf(Size.Zero)
    val icon: Int
        @Composable get() = if (enabled) {
            R.drawable.ic_arrow_drop_up
        } else {
            R.drawable.ic_arrow_drop_down
        }

    fun onEnabled(newValue: Boolean) {
        enabled = newValue
    }

    fun onSelectedIndex(newValue: Int) {
        selectedndex = newValue
        value = items[selectedndex]
    }

    fun onSize(newValue: Size) {
        size = newValue
    }
}

@Composable
fun rememberDropDownStateHolder(currentState: String, listItem: List<String>) = remember {
    DropDownStateHolder(currentState, listItem)
}