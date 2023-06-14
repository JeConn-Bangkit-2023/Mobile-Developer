package com.capstone.jeconn.ui.screen.dashboard.freelancer_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.CustomSearchBar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.VerticalFreelancerCard
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.FreelancerViewModelFactory
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.calculateDistance
import com.capstone.jeconn.utils.calculateDistanceToDecimal

@Composable
fun FreelancerScreen(navHostController: NavHostController, myPaddingValues: PaddingValues) {

    val context = LocalContext.current

    val searchTextState = rememberSaveable() {
        mutableStateOf("")
    }

    val searchEnabledState = rememberSaveable() {
        mutableStateOf(false)
    }
    val searchCategory = remember {
        mutableStateOf(DummyData.entertainmentCategories.values.toList())
    }

    val originalList = remember {
        mutableStateListOf<PublicDataEntity>()
    }

    val freelancerListState = remember {
        derivedStateOf {
            val query = searchTextState.value
            if (query.isBlank()) {
                originalList
            } else {
                originalList.filter { freelancer ->
                    val aboutMeMatch =
                        freelancer.detail_information?.about_me?.contains(query, true) == true
                    val fullNameMatch = freelancer.full_name?.contains(query, true) == true
                    val category = freelancer.jobInformation?.categories?.map {
                        DummyData.entertainmentCategories[it]
                    }
                    val categoryMatch = category?.any {
                        it!!.contains(query, true)
                    } == true
                    aboutMeMatch || fullNameMatch || categoryMatch
                }
            }
        }
    }

    val isLoading = remember {
        mutableStateOf(false)
    }
    if (isLoading.value) {
        CustomDialogBoxLoading()
    }

    val freelancerViewModel: FreelancerViewModel = remember {
        FreelancerViewModelFactory(
            freelancerRepository = Injection.provideFreelancerRepository(
                context = context,
            ),
            chatRepository = Injection.provideChatRepository(
                context = context
            )
        ).create(
            FreelancerViewModel::class.java
        )
    }

    val loadFreelancerListState by rememberUpdatedState(newValue = freelancerViewModel.loadFreelancerListState.value)


    LaunchedEffect(loadFreelancerListState) {
        when (val currentState = loadFreelancerListState) {
            is UiState.Loading -> {
                isLoading.value = true
            }

            is UiState.Success -> {
                isLoading.value = false
                originalList.addAll(currentState.data.filter {
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
                isLoading.value = false
                MakeToast.short(context, currentState.errorMessage)
            }

            else -> {
                //Nothing
                isLoading.value = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomNavbar(
            modifier = Modifier
                .height(90.dp)
                .align(Alignment.TopCenter)
        ) {
            //Nothing
        }

        CustomSearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 12.dp),
            text = searchTextState,
            enabled = searchEnabledState,
            history = searchCategory,
            label = context.getString(R.string.search_freelancer)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(
                    top = 90.dp,
                    bottom = myPaddingValues.calculateBottomPadding()
                ),
        ) {
            item {
                Text(
                    text = context.getString(R.string.nearby_talent),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(28.dp))
            }

            items(freelancerListState.value) { value ->
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
    }
}