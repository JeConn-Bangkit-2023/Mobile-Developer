package com.capstone.jeconn.ui.screen.dashboard.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.HorizontalVacanciesCard
import com.capstone.jeconn.component.card.VerticalFreelancerCard
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.utils.calculateDistance
import com.capstone.jeconn.utils.navigateTo
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(navHostController: NavHostController, paddingValues: PaddingValues) {
    val auth = Firebase.auth
    val context = LocalContext.current
    val publicData = DummyData.publicData.values.toList()
    val vacancies = DummyData.vacancies.values.toList()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
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

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                        navigateTo(navHostController, NavRoute.NotificationScreen)
                    }
                )

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_message),
                    contentDescription = null,
                    modifier = Modifier.size(38.dp)
                )
            }
            Text(
                text = context.getString(R.string.nearby_talent),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(publicData) { user ->
                    if (user.jobInformation != null && user.detail_information != null && user.jobInformation.isOpen) {
                        VerticalFreelancerCard(
                            imageUrl = user.profile_image_url,
                            name = user.full_name,
                            range = calculateDistance(
                                user.jobInformation.location!!,
                                LatLng(51.5074, -0.1278)
                            ),
                            listSkills = user.jobInformation.skills
                        )
                    }
                }
            }

            Text(
                text = context.getString(R.string.nearby_vacancies),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            )
        }

        items(vacancies) { vacancies ->
            val tenant = DummyData.publicData[vacancies.username]!!
            HorizontalVacanciesCard(
                imageUrl = tenant.profile_image_url,
                name = tenant.full_name,
                range = calculateDistance(LatLng(51.5074, -0.1278), vacancies.location),
                timestamp = vacancies.timestamp,
                description = vacancies.description,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}