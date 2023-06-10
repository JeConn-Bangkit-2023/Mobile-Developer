package com.capstone.jeconn.ui.screen.authentication.required_location_screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.AuthViewModelFactory
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.checkLocationPermission
import com.capstone.jeconn.utils.navigateTo
import com.capstone.jeconn.utils.navigateToTop
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun RequiredLocationScreen(
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val gpsService = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val activity = LocalContext.current as Activity
    val requiredLocationViewModel: RequiredLocationViewModel = remember {
        AuthViewModelFactory(Injection.provideAuthRepository(context)).create(
            RequiredLocationViewModel::class.java
        )
    }

    var loadingLocationPush by remember {
        mutableStateOf(false)
    }

    if (loadingLocationPush) {
        CustomDialogBoxLoading()
    }

    val locationState = requiredLocationViewModel.pushLocationState.collectAsState().value
    LaunchedEffect(locationState) {
        when (locationState) {
            is UiState.Loading -> {
                loadingLocationPush = true
            }
            is UiState.Success -> {
                loadingLocationPush = false
                navigateTo(navHostController, NavRoute.BaseScreen)
            }
            is UiState.Error -> {
                loadingLocationPush = false
                MakeToast.short(context, locationState.errorMessage)
            }
            else -> {
                loadingLocationPush = false
            }
        }
    }


    LaunchedEffect(
        checkLocationPermission(context),
        gpsService.isProviderEnabled(LocationManager.GPS_PROVIDER)
    ) {
        if (checkLocationPermission(context) && gpsService.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            requiredLocationViewModel.getLocation()
        }
    }

    val locationPermission = remember {
        mutableStateOf(
            if (checkLocationPermission(context)) {
                LocationVerificationState(
                    icon = R.drawable.ic_done,
                    iconColor = Color.Green,
                    state = false
                )

            } else {
                LocationVerificationState(
                    icon = R.drawable.ic_x,
                    iconColor = Color.Red,
                    state = true
                )
            }
        )
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            locationPermission.value = LocationVerificationState(
                icon = R.drawable.ic_done,
                iconColor = Color.Green,
                state = false
            )
            Log.e("Location", "Accepted")
        } else {
            locationPermission.value = LocationVerificationState(
                icon = R.drawable.ic_x,
                iconColor = Color.Red,
                state = true
            )
            Log.e("Location", "Rejected")
        }
    }

    val gpsPermission = remember {
        mutableStateOf(
            if (gpsService.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                LocationVerificationState(
                    icon = R.drawable.ic_done,
                    iconColor = Color.Green,
                    state = false
                )
            } else {
                LocationVerificationState(
                    icon = R.drawable.ic_x,
                    iconColor = Color.Red,
                    state = true
                )
            }
        )
    }

    val gpsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            gpsPermission.value = LocationVerificationState(
                icon = R.drawable.ic_done,
                iconColor = Color.Green,
                state = false
            )
            Log.e("GPS", "Accepted")
        } else {
            gpsPermission.value = LocationVerificationState(
                icon = R.drawable.ic_x,
                iconColor = Color.Red,
                state = true
            )
            Log.e("GPS", "Rejected")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Subject Text
        Text(
            text = context.getString(R.string.require_location_screen_title),
            fontFamily = Font.QuickSand,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        // Top Image
        Image(
            painter = painterResource(
                id = R.drawable.location_illustration
            ), contentDescription = null, modifier = Modifier
                .height(120.dp)
                .width(250.dp)
        )

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        // Message Text
        Text(
            text = context.getString(R.string.require_location_screen_description),
            fontFamily = Font.QuickSand,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(horizontal = 60.dp)
        )

        Spacer(modifier = Modifier.padding(vertical = 24.dp))

        //Location Permission
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painterResource(id = locationPermission.value.icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(30.dp)
                        .background(locationPermission.value.iconColor)
                        .padding(5.dp)
                )

                Text(
                    text = context.getString(R.string.location),
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                CustomButton(
                    text = context.getString(R.string.allow),
                    enabled = locationPermission.value.state,
                    modifier = Modifier
                        .width(120.dp)
                        .height(40.dp),
                ) {
                    //Request Permission to User
                    locationPermission.value = LocationVerificationState(
                        icon = R.drawable.ic_x,
                        iconColor = Color.Red,
                        state = false
                    )
                    locationPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        //Turn On GPS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = gpsPermission.value.icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(30.dp)
                        .background(gpsPermission.value.iconColor)
                        .padding(horizontal = 5.dp)
                )
                Text(
                    text = context.getString(R.string.gps),
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    CustomButton(
                        text = context.getString(R.string.allow),
                        enabled = gpsPermission.value.state,
                        modifier = Modifier
                            .width(120.dp)
                            .height(40.dp),
                    ) {
                        val request = LocationRequest.create()
                        val builder = LocationSettingsRequest.Builder()
                            .addLocationRequest(request)
                        val client = LocationServices.getSettingsClient(activity)
                        val task = client.checkLocationSettings(builder.build())

                        if (gpsService.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            gpsPermission.value = LocationVerificationState(
                                icon = R.drawable.ic_x,
                                iconColor = Color.Red,
                                state = false
                            )
                        } else {
                            task.addOnFailureListener { exception ->
                                if (exception is ResolvableApiException) {
                                    try {
                                        gpsLauncher.launch(
                                            IntentSenderRequest.Builder(exception.resolution)
                                                .build()
                                        )
                                    } catch (sendEx: IntentSender.SendIntentException) {
                                        // Error handling
                                        Log.e("GPS Task", sendEx.message.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 3.dp))

        Text(
            text = context.getString(R.string.turn_on_gps_guide),
            fontFamily = Font.QuickSand,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Light,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(vertical = 120.dp))

        // Logout Button
        Text(
            text = context.getString(R.string.logout),
            fontFamily = Font.QuickSand,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .clickable {
                    Firebase.auth.signOut()
                    navigateToTop(navHostController, NavRoute.ROOT)
                }
        )
    }
}

data class LocationVerificationState(
    val iconColor: Color,
    val icon: Int,
    val state: Boolean
)