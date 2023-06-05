package com.capstone.jeconn.ui.screen.authentication.required_info_screen


import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.Font
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RequiredInfoScreen(navHostController: NavHostController, isFromRegister: String?) {

    val getParams = isFromRegister.toBoolean()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val requireInfoViewModel: RequireInfoViewModel = remember {
        AuthViewModelFactory(Injection.provideAuthRepository(context)).create(RequireInfoViewModel::class.java)
    }

    val isEmailVerifiedState by requireInfoViewModel.isEmailVerifiedState
    var refreshButtonState by remember {
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()
    val sentEmailVerificationState by rememberUpdatedState(newValue = requireInfoViewModel.sentEmailVerification.value)
    var countdown by rememberSaveable {
        mutableStateOf(
            if (getParams) {
                60
            } else {
                0
            }
        )
    }

    var sentEmailButtonState by rememberSaveable {
        mutableStateOf(true)
    }

    val currentOnStart by rememberUpdatedState(scope)

    LaunchedEffect(countdown) {
        sentEmailButtonState = countdown == 0
    }

    DisposableEffect(sentEmailVerificationState) {

        when (val currentState = sentEmailVerificationState) {
            is UiState.Loading -> {
                sentEmailButtonState = false
            }

            is UiState.Success -> {
                countdown = 60
                MakeToast.short(context, currentState.data)
            }

            is UiState.Error -> {
                MakeToast.short(context, currentState.errorMessage)
                sentEmailButtonState = true
            }

            else -> {
                //Nothing
            }
        }

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                currentOnStart.launch {
                    if (countdown > 0) {
                        for (i in countdown downTo 0) {
                            delay(1000)
                            countdown = i
                        }
                    }
                }
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
            painterResource(id = R.drawable.mail_image),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
        )

        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        Text(
            text = "${context.getString(R.string.verify_ur_account)} ",
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        Text(
            text = "${context.getString(R.string.verify_ur_account_description)} ",
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        )


        //Spacing
        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Text Refresh
            Text(
                text = "${context.getString(R.string.is_verified)} ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            )

            //Button Refresh
            CustomButton(
                text = context.getString(R.string.refresh),
                modifier = Modifier
                    .width(110.dp)
                    .height(40.dp),
                enabled = refreshButtonState
            ) {
                requireInfoViewModel.isEmailVerified()
                when (val currentState = isEmailVerifiedState) {
                    is UiState.Loading -> {
                        refreshButtonState = false
                    }

                    is UiState.Success -> {
                        MakeToast.short(context, currentState.data)
                        refreshButtonState = true
                        navigateTo(navHostController, NavRoute.BaseScreen)
                    }

                    is UiState.Error -> {
                        MakeToast.short(context, currentState.errorMessage)
                        refreshButtonState = true
                    }

                    else -> {
                        refreshButtonState = true
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${context.getString(R.string.not_get_email)} ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            )

            //Resend Button
            CustomButton(
                text = if (countdown == 0) {
                    context.getString(R.string.resend)
                } else {
                    "$countdown"
                },
                enabled = sentEmailButtonState,
                modifier = Modifier
                    .width(110.dp)
                    .height(40.dp),
            ) {
                requireInfoViewModel.sendEmailVerification()
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${context.getString(R.string.back_to_login)} ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            )

            //Logout Button
            CustomButton(
                text = context.getString(R.string.logout),
                modifier = Modifier
                    .width(110.dp)
                    .height(40.dp)
            ) {
                try {
                    Firebase.auth.signOut()
                } catch (e: Exception) {
                    Log.e("Error Sign out", e.message.toString())
                } finally {
                    navigateToTop(navHostController, NavRoute.ROOT)
                }


            }
        }

        Spacer(modifier = Modifier.padding(vertical = 24.dp))
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


