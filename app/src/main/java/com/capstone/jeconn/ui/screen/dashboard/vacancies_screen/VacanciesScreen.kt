package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.capstone.jeconn.component.CustomNavbar

@Composable
fun VacanciesScreen(navHostController: NavHostController, myPaddingValues: PaddingValues) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomNavbar() {

        }
    }

}