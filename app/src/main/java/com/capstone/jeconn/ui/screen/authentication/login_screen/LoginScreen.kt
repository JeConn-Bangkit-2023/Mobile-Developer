package com.capstone.jeconn.ui.screen.authentication.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomIconTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.data.entities.AuthEntity
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.AuthViewModelFactory
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.intentWhatsApp
import com.capstone.jeconn.utils.navigateTo
import com.capstone.jeconn.utils.navigateToTop
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navHostController: NavHostController,
) {
    val auth = Firebase.auth
    if (auth.currentUser != null) {
        if (auth.currentUser!!.isEmailVerified) {
            navigateTo(navHostController, NavRoute.BaseScreen)
        } else {
            navigateTo(navHostController, NavRoute.RequiredInfoScreen)
        }
    } else {

        val context = LocalContext.current

        val loginViewModel: LoginViewModel = remember {
            AuthViewModelFactory(Injection.provideAuthRepository(context)).create(LoginViewModel::class.java)
        }

        val emailState = rememberSaveable {
            mutableStateOf("")
        }
        val passwordState = rememberSaveable {
            mutableStateOf("")
        }

        var buttonState by remember {
            mutableStateOf(true)
        }
        val loginState = loginViewModel.loginState.collectAsStateWithLifecycle().value

        LaunchedEffect(loginState) {
            when (loginState) {
                is UiState.Loading -> {
                    buttonState = false
                }

                is UiState.Success -> {
                    buttonState = true
                    MakeToast.short(context, loginState.data)
                    navigateTo(navHostController, NavRoute.RequiredInfoScreen)
                }

                is UiState.Error -> {
                    buttonState = true
                    MakeToast.short(context, loginState.errorMessage)
                }

                else -> {
                    buttonState = true
                }
            }
        }


        val scope = rememberCoroutineScope()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
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
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Normal,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onBackground
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
            CustomIconTextField(
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
            CustomIconTextField(
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
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground
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
                enabled = buttonState,
                modifier = Modifier.padding(vertical = 24.dp),
            ) {
                when {
                    (emailState.value == "") -> {
                        MakeToast.short(context, context.getString(R.string.empty_email))
                    }

                    (passwordState.value == "") -> {
                        MakeToast.short(context, context.getString(R.string.empty_password))
                    }

                    else -> {
                        scope.launch {
                            loginViewModel.loginUser(
                                AuthEntity(
                                    email = emailState.value,
                                    password = passwordState.value
                                )

//                                AuthEntity(
//                                    username = "jane_smith6",
//                                    email = "fauzanramadhani06@gmail.com",
//                                    password = "fauzanguanteng123",
//                                    fullName = "fauzan ramadhani",
//                                )
                            )
                        }
                    }
                }
            }

            // Need support text
            Text(
                text = context.getString(R.string.need_support),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
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

            Spacer(modifier = Modifier.padding(vertical = 24.dp))
        }

    }
}