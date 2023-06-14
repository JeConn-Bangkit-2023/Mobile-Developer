package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.setValue
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
import com.capstone.jeconn.component.card.HorizontalVacanciesCard
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.data.entities.VacanciesEntity
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.VacanciesViewModelFactory
import com.capstone.jeconn.utils.calculateDistance

@Composable
fun VacanciesScreen(navHostController: NavHostController, myPaddingValues: PaddingValues) {

    val context = LocalContext.current

    val searchTextState = rememberSaveable() {
        mutableStateOf("")
    }

    val searchEnabledState = rememberSaveable() {
        mutableStateOf(false)
    }
    val searchCategoryState = remember {
        mutableStateOf(DummyData.entertainmentCategories.values.toList())
    }
    val originalList = remember {
        mutableStateListOf<VacanciesEntity>()
    }

    val filteredVacanciesList = remember {
        derivedStateOf {
            val query = searchTextState.value
            if (query.isBlank()) {
                originalList
            } else {
                originalList.filter { vacancies ->
                    val descriptionMatch = vacancies.description?.contains(query, true) == true
                    val fullNameMatch = vacancies.full_name?.contains(query, true) == true
                    val category = vacancies.category?.map {
                        DummyData.entertainmentCategories[it]
                    }
                    val categoryMatch = category?.any {
                        it!!.contains(query, true)
                    } == true
                    descriptionMatch || fullNameMatch || categoryMatch
                }
            }
        }
    }

    val vacanciesViewModel: VacanciesViewModel = remember {
        VacanciesViewModelFactory(
            vacanciesRepository = Injection.provideVacanciesRepository(context = context),
            chatRepository = Injection.provideChatRepository(context)
        ).create(
            VacanciesViewModel::class.java
        )
    }

    val vacanciesListState by rememberUpdatedState(newValue = vacanciesViewModel.vacanciesListState.value)

    var isLoading by remember {
        mutableStateOf(false)
    }
    if (isLoading) {
        CustomDialogBoxLoading()
    }

    LaunchedEffect(vacanciesListState) {
        when (val currentState = vacanciesListState) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
                originalList.addAll(currentState.data.sortedByDescending { it.timestamp!! })
            }

            is UiState.Error -> {
                isLoading = false
                MakeToast.short(context, currentState.errorMessage)
            }

            else -> {
                isLoading = false
                //Nothing
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
            history = searchCategoryState,
            label = context.getString(R.string.search_freelancer)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 90.dp,
                    bottom = myPaddingValues.calculateBottomPadding()
                ),
            contentPadding = PaddingValues(top = 12.dp , bottom = 12.dp)
        ) {

            item {
                Text(
                    text = context.getString(R.string.current_vacancies),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 16.dp)
                )
            }

            items(filteredVacanciesList.value) { vacancies ->
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
                    ){
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}