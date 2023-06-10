package com.capstone.jeconn.ui.screen.dashboard.freelancer_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import com.capstone.jeconn.component.card.VerticalFreelancerCard
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.utils.calculateDistance

@Composable
fun FreelancerScreen(navHostController: NavHostController, myPaddingValues: PaddingValues) {
    val searchTextState = rememberSaveable() {
        mutableStateOf("")
    }

    val searchEnabledState = rememberSaveable() {
        mutableStateOf(false)
    }
    val searchEnabledHistory = remember {
        mutableStateListOf<String>()
    }

    val dummyPublicData = DummyData.publicData.toList()

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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 112.dp,
                    bottom = myPaddingValues.calculateBottomPadding()
                )
        ) {
            itemsIndexed(dummyPublicData.toList()) { index, value ->
                val user = value.second
                if (user.jobInformation != null && user.detail_information != null
                ) {
                    if (user.jobInformation.isOpenToOffer) {
                        VerticalFreelancerCard(
                            imageUrl = user.profile_image_url!!,
                            name = user.full_name!!,
                            range = calculateDistance(
                                user.jobInformation.location!!,
                                LocationEntity(51.5074, -0.1278)
                            ),
                            listSkills = user.jobInformation.categories!!
                        )
                    }
                }
            }
        }
    }
}