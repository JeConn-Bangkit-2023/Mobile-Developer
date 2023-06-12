package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen.detail_vacancies_screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.CustomFlatIconButton
import com.capstone.jeconn.component.CustomLabel
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.VacanciesViewModelFactory
import com.capstone.jeconn.utils.getTimeAgo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailVacanciesScreen(
    navHostController: NavHostController,
    id: String?,
    name: String?,
    profileImage: String?,
    distance: String?
) {
    val auth = Firebase.auth.currentUser
    val context = LocalContext.current
    val isLoading = remember {
        mutableStateOf(false)
    }
    if (isLoading.value) {
        CustomDialogBoxLoading()
    }

    val detailVacanciesViewModel: DetailVacanciesViewModel = remember {
        VacanciesViewModelFactory(
            Injection.provideVacanciesRepository(
                context = context,
            ),
            vacanciesId = id!!.toInt(),
            Injection.provideChatRepository(context)
        ).create(
            DetailVacanciesViewModel::class.java
        )
    }

    val isSuccess = remember {
        mutableStateOf(false)
    }

    val openChatState by rememberUpdatedState(newValue = detailVacanciesViewModel.openChatState.value)

    when (val currentState = openChatState) {
        is UiState.Loading -> {
            isLoading.value = true
        }


        is UiState.Success -> {
            isLoading.value = false
            isSuccess.value = true
            LaunchedEffect(isSuccess) {
                if (isSuccess.value) {
                    navHostController.navigate(
                        NavRoute.DetailMessageScreen.navigateWithId(
                            currentState.data.toString(),
                            name!!,
                            Uri.encode(profileImage)
                        )
                    )
                }
            }
        }

        is UiState.Error -> {
            isLoading.value = false
        }

        else -> {
            //Nothing
            isLoading.value = false
        }
    }

    val vacanciesDetailState by rememberUpdatedState(newValue = detailVacanciesViewModel.vacanciesDetailState.value)

    when (val currentState = vacanciesDetailState) {
        is UiState.Loading -> {
            CustomDialogBoxLoading()
        }

        is UiState.Success -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                item {
                    CustomNavbar {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            IconButton(

                                onClick = { navHostController.popBackStack() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                            Text(
                                text = context.getString(R.string.detai_vacancies),
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontFamily = Font.QuickSand,
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CropToSquareImage(
                                imageUrl = Uri.decode(profileImage!!),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            Column {
                                Text(
                                    text = name!!,
                                    style = TextStyle(
                                        fontFamily = Font.QuickSand,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )

                                Text(
                                    text = getTimeAgo(context, currentState.data.timestamp!!),
                                    style = TextStyle(
                                        fontFamily = Font.QuickSand,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )

                            }
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Location icon",
                                tint = MaterialTheme.colorScheme.onBackground

                            )
                            Spacer(modifier = Modifier.padding(8.dp))

                            Text(
                                text = distance!!,
                                style = TextStyle(
                                    fontFamily = Font.QuickSand,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_attach_money),
                                contentDescription = "Icon",
                                tint = MaterialTheme.colorScheme.onBackground

                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text(
                                text = "${currentState.data.start_salary.toString()} - ${currentState.data.end_salary.toString()}",
                                style = TextStyle(
                                    fontFamily = Font.QuickSand,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Text(
                            text = context.getString(R.string.category),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = Font.QuickSand,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                        )

                        Spacer(modifier = Modifier.padding(vertical = 4.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (categoryText in currentState.data.category!!) {
                                CustomLabel(
                                    text = DummyData.entertainmentCategories[categoryText]!!
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(vertical = 8.dp))

                        Text(
                            text = context.getString(R.string.deskripsi),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = Font.QuickSand,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                        )

                        Spacer(modifier = Modifier.padding(vertical = 4.dp))

                        Text(
                            text = currentState.data.description!!,
                            style = TextStyle(
                                fontFamily = Font.QuickSand,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        Spacer(modifier = Modifier.padding(vertical = 40.dp))

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Spacer(modifier = Modifier.weight(1f))

                            if ((auth?.displayName ?: "") != currentState.data.username) {
                                CustomButton(
                                    text = context.getString(R.string.contact_tenant),
                                    enabled = (auth?.displayName ?: "") != currentState.data.username,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    detailVacanciesViewModel.openChat(
                                        auth?.displayName ?: "",
                                        currentState.data.username?: "fauzanramadhani06"
                                    )
                                }
                            }
                        }

                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }

        is UiState.Error -> {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CustomFlatIconButton(
                    icon = Icons.Default.Refresh,
                    label = context.getString(R.string.refresh)
                ) {
                    detailVacanciesViewModel.getVacanciesDetail(id!!.toInt())
                }
            }
            MakeToast.short(context, currentState.errorMessage)
        }

        else -> {
            //Nothing
        }
    }
}