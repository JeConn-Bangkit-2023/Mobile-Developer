package com.capstone.jeconn.ui.screen.authentication.login_screen

import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.TextFieldState
import com.capstone.jeconn.data.entities.AuthEntity
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.AuthViewModelFactory
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.intentWhatsApp
import com.capstone.jeconn.utils.navigateTo
import com.capstone.jeconn.utils.navigateToTop


@Composable
fun LoginScreen(navHostController: NavHostController) {

    val context = LocalContext.current

    val loginViewModel: LoginViewModel = viewModel(
        factory = AuthViewModelFactory(
            Injection.provideAuthRepository(context)
        )
    )

    val emailState = remember {
        TextFieldState()
    }
    val passwordState = remember {
        TextFieldState()
    }

    val buttonState = remember {
        mutableStateOf(true)
    }

    val loginState = loginViewModel.registerState.collectAsState().value

    LaunchedEffect(loginState) {
        when (loginState) {
            is UiState.Loading -> {
                buttonState.value = false
            }

            is UiState.Success -> {
                buttonState.value = true
                MakeToast.short(context, loginState.data)
                navigateTo(navHostController, NavRoute.RequiredInfoScreen)
            }

            is UiState.Error -> {
                buttonState.value = true
                MakeToast.short(context, loginState.errorMessage)
            }

            else -> {
                buttonState.value = true
                Log.e("LoginScreen", "Empty")
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Image
        Image(
            painterResource(id = R.drawable.login_illustration),
            contentDescription = null,
            modifier = Modifier.height(325.dp)
        )

        //Title
        Row {
            Text(
                text = "${context.getString(R.string.login_title)} ", style = TextStyle(
                    fontFamily = Font.QuickSand, fontWeight = FontWeight.Normal, fontSize = 22.sp
                )
            )
            Text(
                text = context.getString(R.string.app_name), style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }

        //Spacing
        Spacer(modifier = Modifier.padding(vertical = 12.dp))

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
            label = "Password",
            type = KeyboardType.Password,
            leadingIcon = R.drawable.ic_lock,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )


        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            //Don't have account
            Text(
                text = "${context.getString(R.string.not_have_account)} ", style = TextStyle(
                    fontFamily = Font.QuickSand, fontWeight = FontWeight.Normal, fontSize = 12.sp
                )
            )
            //Register ButtonText
            Text(text = context.getString(R.string.register), style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary
            ), modifier = Modifier.clickable {
                navigateToTop(navHostController, NavRoute.RegisterScreen)
            })
        }


        //Login Button
        CustomButton(
            text = context.getString(R.string.login),
            enabled = buttonState.value,
            modifier = Modifier.padding(vertical = 24.dp),
        ) {
            when {
                (emailState.text == "") -> {
                    MakeToast.short(context, context.getString(R.string.empty_email))
                }

                (passwordState.text == "") -> {
                    MakeToast.short(context, context.getString(R.string.empty_password))
                }

                else -> {
                    loginViewModel.loginUser(
//                        AuthEntity(
//                            username = usernameState.text,
//                            email = emailState.text,
//                            password = passwordState.text
//                        )
                        AuthEntity(
                            username = "fauzanramadhani06",
                            email = "fauzan1@gmail.com",
                            password = "apahayoo12321"
                        )
                    )
                }
            }
        }

        // Need support text
        Text(
            text = context.getString(R.string.need_support),
            style = TextStyle(
                fontFamily = Font.QuickSand, fontWeight = FontWeight.Normal, fontSize = 12.sp
            ),
        )
        // Customer Service Button
        Text(text = context.getString(R.string.contact_customer_service), style = TextStyle(
            fontFamily = Font.QuickSand,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary
        ), modifier = Modifier.clickable {
            intentWhatsApp(context)
        })
    }
}