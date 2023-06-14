package com.capstone.jeconn.ui.screen.dashboard.home_screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.CustomFlatIconButton
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.HorizontalVacanciesCard
import com.capstone.jeconn.component.card.VerticalFreelancerCard
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.data.entities.VacanciesEntity
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.HomeViewModelFactory
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.calculateDistance
import com.capstone.jeconn.utils.calculateDistanceToDecimal
import com.capstone.jeconn.utils.navigateTo

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    contentRoute: MutableState<Int>
) {
    val context = LocalContext.current

    val isLoading = remember {
        mutableStateOf(false)
    }
    if (isLoading.value) {
        CustomDialogBoxLoading()
    }

    val freelancerList = remember {
        mutableStateListOf<PublicDataEntity>()
    }

    val vacanciesList = remember {
        mutableStateListOf<VacanciesEntity>()
    }

    val homeViewModel: HomeViewModel = remember {
        HomeViewModelFactory(
            freelancerRepository = Injection.provideFreelancerRepository(context),
            vacanciesRepository = Injection.provideVacanciesRepository(context)
        ).create(
            HomeViewModel::class.java
        )
    }

    val loadFreelancerList by rememberUpdatedState(newValue = homeViewModel.loadFreelancerList.value)

    LaunchedEffect(loadFreelancerList) {
        when (val currentState = loadFreelancerList) {
            is UiState.Loading -> {
                isLoading.value = true
            }


            is UiState.Success -> {
                isLoading.value = false
                freelancerList.addAll(currentState.data.filter {
                    it.jobInformation?.isOpenToOffer
                        ?: false
                }.sortedBy {
                    it.jobInformation?.location?.let { it1 ->
                        it.myLocation?.let { it2 ->
                            calculateDistanceToDecimal(
                                it1,
                                it2
                            )
                        }
                    }
                })
            }

            is UiState.Error -> {
                MakeToast.short(context, currentState.errorMessage)
                isLoading.value = false
            }

            else -> {
                //Nothing
                isLoading.value = false
            }
        }
    }

    val vacanciesListState by rememberUpdatedState(newValue = homeViewModel.vacanciesListState.value)

    LaunchedEffect(vacanciesListState) {
        when (val currentState = vacanciesListState) {
            is UiState.Loading -> {
                isLoading.value = true
            }


            is UiState.Success -> {
                isLoading.value = false
                vacanciesList.addAll(currentState.data.sortedByDescending { it.timestamp!! })
            }

            is UiState.Error -> {
                MakeToast.short(context, currentState.errorMessage)
                isLoading.value = false
            }

            else -> {
                //Nothing
                isLoading.value = false
            }
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        item {
            CustomNavbar {
                Text(
                    text = context.getString(R.string.app_name),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                    ),
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { navigateTo(navHostController, NavRoute.NotificationScreen) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                IconButton(
                    onClick = { navigateTo(navHostController, NavRoute.MessageScreen) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_message),
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
            }

            if (freelancerList.size > 0) {
                Spacer(modifier = Modifier.padding(vertical = 12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                ) {

                    Text(
                        text = context.getString(R.string.nearby_talent),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Bold,
                        ),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    CustomFlatIconButton(
                        icon = Icons.Default.KeyboardArrowRight,
                        label = context.getString(R.string.more),
                        isFrontIcon = false
                    ) {
                        contentRoute.value = 2
                    }
                }
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(freelancerList.take(5)) { value ->
                    VerticalFreelancerCard(
                        imageUrl = value.profile_image_url ?: "",
                        name = value.full_name ?: "",
                        range = calculateDistance(
                            value.jobInformation?.location ?: LocationEntity(51.5074, -0.1278),
                            value.myLocation ?: LocationEntity(51.5074, -0.1278)
                        ),
                        listSkills = value.jobInformation?.categories ?: listOf(),
                    ) {
                        navHostController.navigate(
                            NavRoute.DetailFreelancerScreen.navigateWithUsername(
                                value.username ?: "fauzanramadhani06"
                            )
                        ) {
                            launchSingleTop = true
                        }
                    }
                }
            }

            if (vacanciesList.size > 0) {
                Spacer(modifier = Modifier.padding(vertical = 12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = context.getString(R.string.current_vacancies),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Bold,
                        ),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    CustomFlatIconButton(
                        icon = Icons.Default.KeyboardArrowRight,
                        label = context.getString(R.string.more),
                        isFrontIcon = false
                    ) {
                        contentRoute.value = 1
                    }
                }
            }
        }

        items(vacanciesList.take(5)) { vacancies ->
            HorizontalVacanciesCard(
                profileImageUrl = vacancies.imageUrl!!,
                name = vacancies.full_name!!,
                myLocation = vacancies.location!!,
                targetLocation = vacancies.myLocation!!,
                timestamp = vacancies.timestamp!!,
                description = vacancies.description!!,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                navHostController.navigate(
                    NavRoute.DetailVacanciesScreen.navigateWithId(
                        vacancies.id.toString(),
                        vacancies.full_name,
                        Uri.encode(vacancies.imageUrl),
                        calculateDistance(vacancies.location, vacancies.myLocation)
                    )
                ) {
                    launchSingleTop = true
                }
            }
        }
        item {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}