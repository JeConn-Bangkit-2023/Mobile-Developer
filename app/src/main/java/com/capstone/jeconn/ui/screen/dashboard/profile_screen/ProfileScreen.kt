package com.capstone.jeconn.ui.screen.dashboard.profile_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.HorizontalProfileCard
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.ProfileViewModelFactory
import com.capstone.jeconn.utils.navigateTo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    val context = LocalContext.current

    val auth = Firebase.auth

    val profileViewModel: ProfileViewModel = remember {
        ProfileViewModelFactory(Injection.provideProfileRepository(context)).create(
            ProfileViewModel::class.java
        )
    }

    val shortInfoState by rememberUpdatedState(newValue = profileViewModel.shortInfoState.value)


    when (val shortInfo = shortInfoState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }

        is UiState.Success -> {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CustomNavbar(
                    modifier = Modifier
                        .height(128.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CropToSquareImage(
                            imageUrl = shortInfo.data.profile_image_url,
                            contentDescription = null,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                        )

                        Column {
                            Text(
                                text = shortInfo.data.full_name,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = Font.QuickSand,
                                    fontWeight = FontWeight.Bold,
                                )
                            )

                            Text(
                                text = "@${shortInfo.data.username}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = Font.QuickSand,
                                    fontWeight = FontWeight.Normal,
                                )
                            )

                            Text(
                                text = auth.currentUser!!.email!!,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = Font.QuickSand,
                                    fontWeight = FontWeight.Normal,
                                )
                            )
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                ) {

                    HorizontalProfileCard(
                        subject = if (shortInfo.data.detail_information != null) {
                            context.getString(R.string.profile_ready_text)
                        } else {
                            context.getString(R.string.profile_unready_text)

                        },
                        icon = Icons.Default.Person,
                    ) {
                        navigateTo(navHostController, NavRoute.EditDetailInfoScreen)
                    }

                    HorizontalProfileCard(
                        subject = context.getString(R.string.setting),
                        icon = Icons.Default.Settings,
                    ) {
                        navigateTo(navHostController, NavRoute.SettingScreen)
                    }
                }
            }
        }

        is UiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = shortInfo.errorMessage,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Normal,
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                CustomButton(
                    text = context.getString(R.string.refresh),
                    modifier = Modifier
                        .width(120.dp)
                ) {
                    profileViewModel.getShortData()
                }
            }
        }

        else -> {
            //Nothing
        }
    }

}