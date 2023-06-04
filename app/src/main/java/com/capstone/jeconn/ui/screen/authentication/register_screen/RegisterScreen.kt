package com.capstone.jeconn.ui.screen.authentication.register_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.data.entities.AuthEntity
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.AuthViewModelFactory
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.intentWhatsApp
import com.capstone.jeconn.utils.navigateToTop

@Composable
fun RegisterScreen(navHostController: NavHostController) {
    val emailState = rememberSaveable {
        mutableStateOf("")
    }

    val usernameState = rememberSaveable {
        mutableStateOf("")
    }

    val fullNameState = rememberSaveable {
        mutableStateOf("")
    }


    val passwordState = rememberSaveable {
        mutableStateOf("")
    }

    val passwordConfirmationState = rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val registerViewModel: RegisterViewModel = remember {
        AuthViewModelFactory(Injection.provideAuthRepository(context)).create(RegisterViewModel::class.java)
    }

    val buttonState = remember {
        mutableStateOf(true)
    }

    val registerState = registerViewModel.registerState.collectAsState().value

    LaunchedEffect(registerState) {
        when (registerState) {
            is UiState.Loading -> {
                buttonState.value = false
            }

            is UiState.Success -> {
                buttonState.value = true
                Toast.makeText(context, registerState.data, Toast.LENGTH_SHORT).show()
                navHostController.navigate(
                    NavRoute.RequiredInfoScreen.navigateFromRegister(
                        isFromRegister = "true"
                    )
                ) {
                    launchSingleTop = true
                }
            }

            is UiState.Error -> {
                buttonState.value = true
                Toast.makeText(context, registerState.errorMessage, Toast.LENGTH_SHORT).show()
            }

            else -> {
                buttonState.value = true
                Log.e("LoginScreen", "Empty")
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Image
        Image(
            painterResource(id = R.drawable.register_image),
            contentDescription = null,
            modifier = Modifier
                .height(325.dp)
        )

        //Title
        Row {
            Text(
                text = "${context.getString(R.string.register_title)} ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp
                )
            )
            Text(
                text = context.getString(R.string.app_name),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }

        //Spacing
        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        //Username Text Field
        CustomTextField(
            state = usernameState,
            label = "Username",
            type = KeyboardType.Text,
            leadingIcon = R.drawable.ic_account_key,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        //Full Name Text Field
        CustomTextField(
            state = fullNameState,
            label = context.getString(R.string.fullname),
            type = KeyboardType.Text,
            leadingIcon = Icons.Default.Person,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        //Email Text Field
        CustomTextField(
            state = emailState,
            label = "Email",
            type = KeyboardType.Email,
            leadingIcon = R.drawable.ic_email,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        //Password Text Field
        CustomTextField(
            state = passwordState,
            label = context.getString(R.string.password),
            type = KeyboardType.Password,
            leadingIcon = R.drawable.ic_lock,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        //Confirm Password Text Field
        CustomTextField(
            state = passwordConfirmationState,
            label = context.getString(R.string.confirm_password),
            type = KeyboardType.Password,
            leadingIcon = R.drawable.ic_lock,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            //Don't have account
            Text(
                text = "${context.getString(R.string.already_have_account)} ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
            //Login ButtonText
            Text(
                text = context.getString(R.string.login),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .clickable {
                        navigateToTop(navHostController, NavRoute.LoginScreen)
                    }
            )
        }

        //Register Button
        CustomButton(
            text = context.getString(R.string.register),
            enabled = buttonState.value,
            modifier = Modifier
                .padding(vertical = 24.dp),
        ) {
            when {
                (usernameState.value == "") -> {
                    MakeToast.short(context, context.getString(R.string.empty_username))
                }

                (fullNameState.value == "") -> {
                    MakeToast.short(context, context.getString(R.string.empty_full_name))
                }

                (emailState.value == "") -> {
                    MakeToast.short(context, context.getString(R.string.empty_email))
                }

                (passwordState.value == "") -> {
                    MakeToast.short(context, context.getString(R.string.empty_password))
                }

                (passwordState.value == "") -> {
                    MakeToast.short(context, context.getString(R.string.empty_password_conf))
                }

                (passwordState.value != passwordConfirmationState.value) -> {
                    MakeToast.short(context, context.getString(R.string.password_confirm_wrong))
                }

                else -> {
                    registerViewModel.registerUser(
                        AuthEntity(
                            username = usernameState.value,
                            email = emailState.value,
                            password = passwordState.value
                        )
                    )
                }
            }

        }

        // Need support text
        Text(
            text = context.getString(R.string.need_support),
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ),
        )
        // Customer Service Button
        Text(
            text = context.getString(R.string.contact_customer_service),
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .clickable {
                    intentWhatsApp(context)
                }
        )
    }
}