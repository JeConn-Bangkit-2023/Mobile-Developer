package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.CustomSearchBar
import com.capstone.jeconn.component.card.HorizontalVacanciesCard
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.utils.calculateDistance

@Composable
fun VacanciesScreen(navHostController: NavHostController, myPaddingValues: PaddingValues) {

    val searchTextState = rememberSaveable() {
        mutableStateOf("")
    }

    val searchEnabledState = rememberSaveable() {
        mutableStateOf(false)
    }
    val searchEnabledHistory = remember {
        mutableStateListOf<String>()
    }

    val dummyVacanciesList = DummyData.vacancies.toList()

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
            history = searchEnabledHistory
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 112.dp,
                    bottom = myPaddingValues.calculateBottomPadding()
                )
        ) {
            itemsIndexed(dummyVacanciesList.toList()) { index, value ->
                val tenant = DummyData.publicData[value.second.username]!!
                HorizontalVacanciesCard(
                    profileImageUrl = tenant.profile_image_url!!,
                    name = tenant.full_name!!,
                    range = calculateDistance(LocationEntity(51.5074, -0.1278), value.second.location!!),
                    timestamp = value.second.timestamp!!,
                    description = value.second.description!!,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                ){
                    navHostController.navigate(NavRoute.DetailVacanciesScreen.navigateWithId(index.toString()))
                }
            }
        }
    }
}