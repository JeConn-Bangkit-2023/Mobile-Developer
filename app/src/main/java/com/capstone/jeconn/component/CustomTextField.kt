package com.capstone.jeconn.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.capstone.jeconn.R


class TextFieldState {
    var text by mutableStateOf("")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    state: TextFieldState = remember { TextFieldState() },
    length: Int = 50,
    type: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    leadingIcon: Int? = null,
) {
    val focusManager = LocalFocusManager.current

    val isPassword = remember {
        mutableStateOf(type == KeyboardType.Password || type == KeyboardType.NumberPassword)
    }
    val visible = remember {
        mutableStateOf(false)
    }
    val trailingIcon = when {
        isPassword.value -> {
            if (visible.value) {
                painterResource(id = R.drawable.ic_eye_visible)
            } else {
                painterResource(id = R.drawable.ic_eye_visibility_off)
            }
        }

        else -> {
            painterResource(id = R.drawable.ic_close)
        }
    }

    OutlinedTextField(
        value = state.text,
        onValueChange = {
            if (it.length <= length) state.text = it
        },
        label = {
            Text(text = label)
        },
        modifier = modifier,
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            if (isPassword.value) {
                                visible.value = !visible.value
                            } else {
                                state.text = ""
                            }
                        },
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        trailingIcon =
        {
            Icon(
                painter = trailingIcon,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        if (isPassword.value) {
                            visible.value = !visible.value
                        } else {
                            state.text = ""
                        }
                    },
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = type,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        visualTransformation = if (isPassword.value && !visible.value)
            PasswordVisualTransformation() else VisualTransformation.None,
    )
}